package dao;


import view.Session;
import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.JenisBbmModel;

public class JenisBbmDaoImpl implements JenisBbmDao {
    @Override public List<JenisBbmModel> getAll() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        List<JenisBbmModel> hasil = new ArrayList<>();
        String sql = "SELECT id_jenis_bbm, nama_bbm, satuan, status_aktif FROM jenis_bbm ORDER BY id_jenis_bbm";
        try (Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()) {
            while (rs.next()) hasil.add(new JenisBbmModel(rs.getInt("id_jenis_bbm"), rs.getString("nama_bbm"), rs.getString("satuan"), rs.getString("status_aktif")));
        }
        return hasil;
    }
    @Override public boolean insert(JenisBbmModel d) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql="INSERT INTO jenis_bbm (nama_bbm,satuan,status_aktif) VALUES (?,?, 'AKTIF')";
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) { ps.setString(1,d.getNamaBbm()); ps.setString(2,d.getSatuan()); return ps.executeUpdate()>0; }
    }
    @Override public boolean update(JenisBbmModel d) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql="UPDATE jenis_bbm SET nama_bbm=?, satuan=? WHERE id_jenis_bbm=?";
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) { ps.setString(1,d.getNamaBbm()); ps.setString(2,d.getSatuan()); ps.setInt(3,d.getIdJenisBbm()); return ps.executeUpdate()>0; }
    }
    @Override public boolean updateStatus(int id, String status) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (!"AKTIF".equals(status) && !"NONAKTIF".equals(status)) throw new IllegalArgumentException("Status tidak valid.");
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement("UPDATE jenis_bbm SET status_aktif=? WHERE id_jenis_bbm=?")) { ps.setString(1,status); ps.setInt(2,id); return ps.executeUpdate()>0; }
    }
    @Override public int deleteOrDeactivate(int id) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String cek="SELECT id_jenis_bbm FROM jenis_bbm WHERE id_jenis_bbm=? FOR UPDATE";
        String ref="SELECT 1 FROM distribusi_bbm WHERE id_jenis_bbm=? "
                + "UNION ALL SELECT 1 FROM penerimaan_bbm WHERE id_jenis_bbm=? "
                + "UNION ALL SELECT 1 FROM stok_penyimpanan WHERE id_jenis_bbm=? "
                + "UNION ALL SELECT 1 FROM stok_spbu WHERE id_jenis_bbm=? LIMIT 1";
        try(Connection c=Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                try(PreparedStatement ps=c.prepareStatement(cek)){ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){if(!rs.next()){c.rollback();return 0;}}}
                boolean dipakai;
                try(PreparedStatement ps=c.prepareStatement(ref)){for(int i=1;i<=4;i++)ps.setInt(i,id);try(ResultSet rs=ps.executeQuery()){dipakai=rs.next();}}
                if(dipakai){try(PreparedStatement ps=c.prepareStatement("UPDATE jenis_bbm SET status_aktif='NONAKTIF' WHERE id_jenis_bbm=?")){ps.setInt(1,id);ps.executeUpdate();}c.commit();return 2;}
                try(PreparedStatement ps=c.prepareStatement("DELETE FROM jenis_bbm WHERE id_jenis_bbm=?")){ps.setInt(1,id);ps.executeUpdate();}
                c.commit();return 1;
            } catch(SQLException ex){
                c.rollback();
                if("23000".equals(ex.getSQLState())){try(PreparedStatement ps=c.prepareStatement("UPDATE jenis_bbm SET status_aktif='NONAKTIF' WHERE id_jenis_bbm=?")){ps.setInt(1,id);int n=ps.executeUpdate();c.commit();return n>0?2:0;}}
                throw ex;
            }
        }
    }
}
