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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 * @author Ovcharov Alexey
 */
@SuppressWarnings("serial")
public class MainForm extends javax.swing.JFrame {

    private static final String END_DAY = "<<end day>>";
    private static final String PAUSE = "<<pause>>";
    private static final String BEGIN_DAY = "<<begin day>>";
    private File file;
    private final transient List<TrackItem> tracks = new ArrayList<>();
    private LocalDate currentDay = LocalDate.now();

    private void write(TrackItem newTrack) {
        TrackItem last = getLastTrackItem();
        String newTrackId = newTrack.getTrackId();
        if (!last.getTrackId().equals(newTrackId)) {
            if (!END_DAY.equals(newTrackId)) {
                tracks.removeIf(trackItem -> END_DAY.equals(trackItem.getTrackId()));
            }
            last = getLastTrackItem();
            if (!last.getTrackId().equals(newTrackId)) {
                tracks.add(newTrack);
                persist();
            }
            listTracks.updateUI();
        }
    }

    private TrackItem getLastTrackItem() {
        TrackItem last;
        if (!tracks.isEmpty()) {
            last = tracks.get(tracks.size() - 1);
        } else {
            last = new TrackItem(BEGIN_DAY, new Date());
        }
        return last;
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
        if (tracks.stream()
                  .noneMatch(trackItem -> END_DAY.equals(trackItem.getTrackId()))) {
            JOptionPane.showMessageDialog(this,
                    "Missing end of day entry, report may be incorrect",
                    "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
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
        SimpleDateFormat format = new SimpleDateFormat(Constants.REPORT_TIME_FORMAT);
        List<List<String>> repData = new ArrayList<>();
        for (ReportItem reportItem : tRep.values()) {
            long timeDiff = reportItem.getTimeDiff();
            String date = format.format(toDate(timeDiff));
            TrackItem trackItem = reportItem.getTrackItem();
            String description = trackItem.getDescription() == null ? "" : trackItem.getDescription();
            if (!PAUSE.equals(trackItem.getTrackId())) {
                repData.add(Arrays.asList(trackItem.getTrackId(), description, date));
            }
        }
        ReportForm reportForm = new ReportForm(this, true);
        String currentDateAsString = currentDay.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        reportForm.setTitle("Report for " + currentDateAsString);
        reportForm.setRep(repData);
        reportForm.setVisible(true);
    }

    private void endDay() {
        tracks.removeIf(trackItem -> END_DAY.equals(trackItem.getTrackId()));
        TrackItem newTrack = new TrackItem(END_DAY, new Date());
        tracks.add(newTrack);
        persist();
        listTracks.updateUI();
    }

    private void persist() {
        Path path = Paths.get(file.getAbsolutePath());
        try {
            byte[] bytes = tracks.stream()
                                 .map(TrackItem::asCsvString)
                                 .collect(Collectors.joining("\n"))
                                 .getBytes(StandardCharsets.UTF_8);
            Files.write(path, bytes);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
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
        buttonPause = new javax.swing.JButton();
        buttonPrevDay = new javax.swing.JButton();
        buttonNextDay = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Time Tracker");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        labelTaskId.setText("TaskId");

        buttonAddTrack.setBackground(new java.awt.Color(51, 255, 51));
        buttonAddTrack.setText("Add track");
        buttonAddTrack.setPreferredSize(new java.awt.Dimension(80, 24));
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
        buttonReport.setPreferredSize(new java.awt.Dimension(80, 24));
        buttonReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReportActionPerformed(evt);
            }
        });

        buttonEndDay.setBackground(new java.awt.Color(255, 102, 102));
        buttonEndDay.setText("End day");
        buttonEndDay.setPreferredSize(new java.awt.Dimension(80, 24));
        buttonEndDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEndDayActionPerformed(evt);
            }
        });

        labelCurrentDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        buttonPause.setBackground(new java.awt.Color(255, 255, 51));
        buttonPause.setText("Pause");
        buttonPause.setPreferredSize(new java.awt.Dimension(80, 24));
        buttonPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPauseActionPerformed(evt);
            }
        });

        buttonPrevDay.setText("<");
        buttonPrevDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrevDayActionPerformed(evt);
            }
        });

        buttonNextDay.setText(">");
        buttonNextDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextDayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addGroup(layout.createSequentialGroup()
                                                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                      .addComponent(textBoxTaskId, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                      .addComponent(textBoxDescription, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                      .addComponent(labelDescription, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                      .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                      .addGroup(layout.createSequentialGroup()
                                                                                                      .addComponent(buttonAddTrack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                      .addComponent(buttonEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                      .addComponent(buttonPause, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                      .addComponent(buttonReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                      .addGap(0, 0, Short.MAX_VALUE))
                                                      .addGroup(layout.createSequentialGroup()
                                                                      .addComponent(labelTaskId)
                                                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                      .addComponent(buttonPrevDay)
                                                                      .addGap(18, 18, 18)
                                                                      .addComponent(buttonNextDay))
                                                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                                  .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                  .addComponent(labelCurrentDay, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                      .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                  .addContainerGap()
                                                                                  .addComponent(labelCurrentDay, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                  .addComponent(labelTaskId)
                                                                                                  .addComponent(buttonPrevDay)
                                                                                                  .addComponent(buttonNextDay))
                                                                                  .addGap(10, 10, 10)
                                                                                  .addComponent(textBoxTaskId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                  .addComponent(labelDescription)
                                                                                  .addGap(7, 7, 7)
                                                                                  .addComponent(textBoxDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                                                                                  .addComponent(buttonAddTrack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addComponent(buttonEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addComponent(buttonPause, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addComponent(buttonReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                  .addGap(18, 18, 18)
                                                                                  .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void buttonPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPauseActionPerformed
        pause();
    }//GEN-LAST:event_buttonPauseActionPerformed

    private void buttonPrevDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrevDayActionPerformed
        prevDay();
    }//GEN-LAST:event_buttonPrevDayActionPerformed

    private void prevDay() {
        nextDay();
    }

    private void nextDay() {
        currentDay = currentDay.minusDays(1);
        initSettings();
    }

    private void buttonNextDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextDayActionPerformed
        currentDay = currentDay.plusDays(1);
        initSettings();
    }//GEN-LAST:event_buttonNextDayActionPerformed

    private void pause() {
        TrackItem pauseTrack = new TrackItem(PAUSE, new Date());
        write(pauseTrack);
    }

    private void onLoad() {
        setLocationRelativeTo(null);
        initSettings();
    }

    private void initSettings() {
        String userHome = System.getProperty("user.home");
        String currentDateAsString = currentDay.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        labelCurrentDay.setText("Today: " + currentDateAsString);
        String dirName = userHome + File.separator + "sTimeTracks";
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(dir, "sTime-track-" + currentDateAsString + ".cvs");
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
                tracks.clear();
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
        if (taskId != null
                && !taskId.isEmpty()
                && !taskId.equalsIgnoreCase(BEGIN_DAY)
                && !taskId.equalsIgnoreCase(END_DAY)
                && !taskId.equalsIgnoreCase(PAUSE)
        ) {
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
    private javax.swing.JButton buttonNextDay;
    private javax.swing.JButton buttonPause;
    private javax.swing.JButton buttonPrevDay;
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
