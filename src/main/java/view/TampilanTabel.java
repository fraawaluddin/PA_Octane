package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public final class TampilanTabel {

    private TampilanTabel() {
    }

    public static void rapikanSemua(Container root) {
        if (root == null) {
            return;
        }
        for (Component component : root.getComponents()) {
            if (component instanceof JTable) {
                rapikan((JTable) component);
            } else if (component instanceof Container) {
                rapikanSemua((Container) component);
            }
        }
    }

    public static void rapikan(JTable table) {
        if (table == null || table.getColumnModel().getColumnCount() == 0) {
            return;
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFillsViewportHeight(true);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new java.awt.Dimension(1, 1));
        table.setGridColor(new java.awt.Color(210, 218, 226));

        if (table.getTableHeader() != null) {
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(true);
            table.getTableHeader().setPreferredSize(
                    new java.awt.Dimension(table.getTableHeader().getPreferredSize().width, 34));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            // Pertahankan kolom ID yang memang sengaja disembunyikan oleh form CRUD.
            if (column.getMinWidth() == 0 && column.getMaxWidth() == 0) {
                continue;
            }
            String title = String.valueOf(column.getHeaderValue()).toLowerCase();
            column.setPreferredWidth(lebarKolom(title));
        }
    }

    private static int lebarKolom(String title) {
        if (title.contains("aksi")) return 205;
        if (title.equals("id") || title.startsWith("id ")) return 78;
        if (title.contains("tanggal") || title.contains("waktu")) return 155;
        if (title.contains("alamat") || title.contains("keterangan")) return 245;
        if (title.contains("lokasi") || title.contains("penempatan")) return 225;
        if (title.contains("nama") || title.contains("petugas") || title.contains("username")) return 180;
        if (title.contains("spbu")) return 185;
        if (title.contains("bbm") || title.contains("jenis")) return 145;
        if (title.contains("kualifikasi") || title.contains("sertifikasi")) return 225;
        if (title.contains("jumlah") || title.contains("stok") || title.contains("volume")) return 125;
        if (title.contains("status")) return 115;
        if (title.contains("satuan")) return 100;
        if (title.contains("peran")) return 175;
        return 150;
    }
}
