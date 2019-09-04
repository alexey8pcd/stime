package ru.alejov.util.stime;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractListModel;

/**
 *
 * @author Ovcharov Alexey
 */
@SuppressWarnings("serial")
public class MainForm extends javax.swing.JFrame {

    private File file;
    private final transient List<TrackItem> tracks = new ArrayList<>();

    private void write(TrackItem newTrack) {
        tracks.add(newTrack);
        Path path = Paths.get(file.getAbsolutePath());
        byte[] bytes = newTrack.asCsvString().concat("\n").getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(path, bytes, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
        listTracks.updateUI();
    }

    private void writeTrack(int clickCount) {
        int selectedIndex = listTracks.getSelectedIndex();
        if (!tracks.isEmpty() && clickCount == 2 && selectedIndex >= 0
                && selectedIndex < tracks.size()) {
            TrackItem oldTrack = tracks.get(selectedIndex);
            TrackItem newTrack = new TrackItem(oldTrack.getTrackId(), new Date());
            newTrack.setDescription(oldTrack.getDescription());
            write(newTrack);
        }
    }

    private static Date toDate(long diffInMillis) {
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMillis) % 60;
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
        int hours = (int) TimeUnit.MILLISECONDS.toHours(diffInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    private void report() {
        Map<String, ReportItem> tRep = new LinkedHashMap<>();
        for (int i = 1; i < tracks.size(); ++i) {
            TrackItem prev = tracks.get(i - 1);
            TrackItem curr = tracks.get(i);
            String key = (prev.getTrackId() + prev.getDescription()).replace(" ", "");
            ReportItem value = tRep.get(key);
            long diff = curr.getDate().getTime() - prev.getDate().getTime();
            if (value == null) {
                tRep.put(key, new ReportItem(prev, diff));
            } else {
                value.increase(diff);
            }
        }
        SimpleDateFormat format = new SimpleDateFormat(Constants.TIME_FORMAT);
        List<List<String>> repData = new ArrayList<>();
        for (ReportItem reportItem : tRep.values()) {
            long timeDiff = reportItem.getTimeDiff();
            String date = format.format(toDate(timeDiff));
            TrackItem trackItem = reportItem.getTrackItem();
            String description = trackItem.getDescription() == null ? "" : trackItem.getDescription();
            repData.add(Arrays.asList(trackItem.getTrackId(), description, date));
        }
        ReportForm reportForm = new ReportForm(this, true);
        reportForm.setRep(repData);
        reportForm.setVisible(true);
    }

    private void endDay() {
        write(new TrackItem("#endDay", new Date()));
    }

    public MainForm() {
        initComponents();
        listTracks.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return tracks.size();
            }

            @Override
            public String getElementAt(int index) {
                return tracks.get(index).toString();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTaskId = new javax.swing.JLabel();
        textBoxTaskId = new javax.swing.JTextField();
        buttonAddTrack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listTracks = new javax.swing.JList<>();
        labelDescription = new javax.swing.JLabel();
        textBoxDescription = new javax.swing.JTextField();
        buttonReport = new javax.swing.JButton();
        buttonEndDay = new javax.swing.JButton();
        labelCurrentDay = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Time Tracker");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        labelTaskId.setText("TaskId");

        buttonAddTrack.setText("Add track");
        buttonAddTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddTrackActionPerformed(evt);
            }
        });

        listTracks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listTracksMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(listTracks);

        labelDescription.setText("Description");

        buttonReport.setText("Report");
        buttonReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReportActionPerformed(evt);
            }
        });

        buttonEndDay.setText("End day");
        buttonEndDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEndDayActionPerformed(evt);
            }
        });

        labelCurrentDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(textBoxTaskId)
                    .addComponent(textBoxDescription)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonAddTrack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonEndDay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonReport))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTaskId)
                            .addComponent(labelDescription))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelCurrentDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCurrentDay, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelTaskId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textBoxTaskId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelDescription)
                .addGap(7, 7, 7)
                .addComponent(textBoxDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAddTrack)
                    .addComponent(buttonReport)
                    .addComponent(buttonEndDay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAddTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddTrackActionPerformed
        onStart();
    }//GEN-LAST:event_buttonAddTrackActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        onLoad();
    }//GEN-LAST:event_formWindowOpened

    private void listTracksMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTracksMousePressed
        writeTrack(evt.getClickCount());
    }//GEN-LAST:event_listTracksMousePressed

    private void buttonReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReportActionPerformed
        report();
    }//GEN-LAST:event_buttonReportActionPerformed

    private void buttonEndDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEndDayActionPerformed
        endDay();
    }//GEN-LAST:event_buttonEndDayActionPerformed

    private void onLoad() {
        setLocationRelativeTo(null);
        String userHome = System.getProperty("user.home");
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        labelCurrentDay.setText("Today: " + date);
        String dirName = userHome + File.separator + "sTimeTracks";
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(dir, "sTime-track-" + date + ".cvs");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                System.exit(1);
            }
        } else {
            Path path = Paths.get(file.getAbsolutePath());
            try {
                List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
                for (String string : allLines) {
                    tracks.add(new TrackItem(string));
                }
                listTracks.updateUI();
            } catch (ParseException | IOException ex) {
                ex.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    private void onStart() {
        String taskId = textBoxTaskId.getText();
        if (taskId != null && !taskId.isEmpty()) {
            TrackItem trackItem = new TrackItem(taskId, new Date());
            String desc = textBoxDescription.getText();
            if (desc != null && !desc.isEmpty()) {
                trackItem.setDescription(desc);
            }
            write(trackItem);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace(System.err);
            System.exit(2);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddTrack;
    private javax.swing.JButton buttonEndDay;
    private javax.swing.JButton buttonReport;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCurrentDay;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelTaskId;
    private javax.swing.JList<String> listTracks;
    private javax.swing.JTextField textBoxDescription;
    private javax.swing.JTextField textBoxTaskId;
    // End of variables declaration//GEN-END:variables
}
