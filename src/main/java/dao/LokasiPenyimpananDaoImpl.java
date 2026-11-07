package dao;


import view.Session;
import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.LokasiPenyimpananModel;

public class LokasiPenyimpananDaoImpl implements LokasiPenyimpananDao {
    @Override public List<LokasiPenyimpananModel> getAll() throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        List<LokasiPenyimpananModel> hasil = new ArrayList<>();
        String sql = "SELECT id_lokasi_penyimpanan, nama_lokasi, alamat, status_aktif FROM lokasi_penyimpanan ORDER BY id_lokasi_penyimpanan";
        try (Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()) {
            while (rs.next()) hasil.add(new LokasiPenyimpananModel(rs.getInt("id_lokasi_penyimpanan"), rs.getString("nama_lokasi"), rs.getString("alamat"), rs.getString("status_aktif")));
        }
        return hasil;
    }
    @Override public boolean insert(LokasiPenyimpananModel d) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql="INSERT INTO lokasi_penyimpanan (nama_lokasi,alamat,status_aktif) VALUES (?,?, 'AKTIF')";
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) { ps.setString(1,d.getNamaLokasi()); ps.setString(2,d.getAlamat()); return ps.executeUpdate()>0; }
    }
    @Override public boolean update(LokasiPenyimpananModel d) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String sql="UPDATE lokasi_penyimpanan SET nama_lokasi=?, alamat=? WHERE id_lokasi_penyimpanan=?";
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) { ps.setString(1,d.getNamaLokasi()); ps.setString(2,d.getAlamat()); ps.setInt(3,d.getIdLokasiPenyimpanan()); return ps.executeUpdate()>0; }
    }
    @Override public boolean updateStatus(int id, String status) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        if (!"AKTIF".equals(status) && !"NONAKTIF".equals(status)) throw new IllegalArgumentException("Status tidak valid.");
        try(Connection c=Koneksi.getConnection(); PreparedStatement ps=c.prepareStatement("UPDATE lokasi_penyimpanan SET status_aktif=? WHERE id_lokasi_penyimpanan=?")) { ps.setString(1,status); ps.setInt(2,id); return ps.executeUpdate()>0; }
    }
    @Override public int deleteOrDeactivate(int id) throws SQLException {
        Session.requireRole(Session.ADMINISTRATOR);

        String cek="SELECT id_lokasi_penyimpanan FROM lokasi_penyimpanan WHERE id_lokasi_penyimpanan=? FOR UPDATE";
        String ref="SELECT 1 FROM petugas_penyimpanan WHERE id_lokasi_penyimpanan=? "
                + "UNION ALL SELECT 1 FROM distribusi_bbm WHERE id_lokasi_penyimpanan=? "
                + "UNION ALL SELECT 1 FROM penerimaan_bbm WHERE id_lokasi_penyimpanan=? "
                + "UNION ALL SELECT 1 FROM stok_penyimpanan WHERE id_lokasi_penyimpanan=? LIMIT 1";
        try(Connection c=Koneksi.getConnection()) {
            c.setAutoCommit(false);
            try {
                try(PreparedStatement ps=c.prepareStatement(cek)){ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){if(!rs.next()){c.rollback();return 0;}}}
                boolean dipakai;
                try(PreparedStatement ps=c.prepareStatement(ref)){for(int i=1;i<=4;i++)ps.setInt(i,id);try(ResultSet rs=ps.executeQuery()){dipakai=rs.next();}}
                if(dipakai){try(PreparedStatement ps=c.prepareStatement("UPDATE lokasi_penyimpanan SET status_aktif='NONAKTIF' WHERE id_lokasi_penyimpanan=?")){ps.setInt(1,id);ps.executeUpdate();}c.commit();return 2;}
                try(PreparedStatement ps=c.prepareStatement("DELETE FROM lokasi_penyimpanan WHERE id_lokasi_penyimpanan=?")){ps.setInt(1,id);ps.executeUpdate();}
                c.commit();return 1;
            } catch(SQLException ex){
                c.rollback();
                if("23000".equals(ex.getSQLState())){try(PreparedStatement ps=c.prepareStatement("UPDATE lokasi_penyimpanan SET status_aktif='NONAKTIF' WHERE id_lokasi_penyimpanan=?")){ps.setInt(1,id);int n=ps.executeUpdate();c.commit();return n>0?2:0;}}
                throw ex;
            }
        }
    }
}
