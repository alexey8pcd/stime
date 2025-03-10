package ru.alejov.util.stime;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 * @author Ovcharov Alexey
 */
@SuppressWarnings("serial")
public class MainForm extends javax.swing.JFrame {

    private static final String HELP_ABOUT_TEXT = ""
            + "Simple Time Tracker (v. 2025.1)\n"
            + "\n"
            + "The program allows you to process labor costs for tasks:\n"
            + "adding, editing, deleting records, creating a report.\n"
            + "The data is stored in files in the user directory, in\n"
            + "the 'sTimeTracks' subfolder. Each day - a separate file.\n"
            + "\n"
            + "Author: Alexey Ovcharov, e-mail: alexey8rus@mail.ru";

    private final transient List<TrackItem> tracks = new CopyOnWriteArrayList<>();
    private final LocalDate today = LocalDate.now();
    private final Set<String> lastTasks = new TreeSet<>();
    
    private LocalDate currentDay = today;
    private File file;
    
    public MainForm() {
        initComponents();
        listTracks.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return tracks.size();
            }

            @Override
            public String getElementAt(int index) {
                TrackItem track = tracks.get(index);
                return track.toString();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenuTracks = new javax.swing.JPopupMenu();
        menuItemEdit = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        panelTop = new javax.swing.JPanel();
        panelTaskId = new javax.swing.JPanel();
        labelCurrentDay = new javax.swing.JLabel();
        panelChangeDayButtons = new javax.swing.JPanel();
        buttonPrevDay = new javax.swing.JButton();
        buttonNextDay = new javax.swing.JButton();
        buttonHelp = new javax.swing.JButton();
        panelEnterTask = new javax.swing.JPanel();
        comboBoxTaskId = new javax.swing.JComboBox<>();
        labelTaskId = new javax.swing.JLabel();
        panelButtons = new javax.swing.JPanel();
        buttonAddTrack = new javax.swing.JButton();
        buttonPause = new javax.swing.JButton();
        buttonEndDay = new javax.swing.JButton();
        buttonReport = new javax.swing.JButton();
        scrollPaneTracks = new javax.swing.JScrollPane();
        listTracks = new javax.swing.JList<>();

        menuItemEdit.setText("Edit...");
        menuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEditActionPerformed(evt);
            }
        });
        popupMenuTracks.add(menuItemEdit);

        menuItemDelete.setText("Delete");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        popupMenuTracks.add(menuItemDelete);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Time Tracker");
        setPreferredSize(new java.awt.Dimension(400, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(0, 5));

        panelTop.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTop.setLayout(new java.awt.GridLayout(3, 1, 10, 10));

        panelTaskId.setLayout(new java.awt.GridLayout(1, 3, 20, 0));

        labelCurrentDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCurrentDay.setText("Date: 01.01.2018");
        panelTaskId.add(labelCurrentDay);

        panelChangeDayButtons.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelChangeDayButtons.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        buttonPrevDay.setText("<");
        buttonPrevDay.setToolTipText("Prev day");
        buttonPrevDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrevDayActionPerformed(evt);
            }
        });
        panelChangeDayButtons.add(buttonPrevDay);

        buttonNextDay.setText(">");
        buttonNextDay.setToolTipText("Next day");
        buttonNextDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextDayActionPerformed(evt);
            }
        });
        panelChangeDayButtons.add(buttonNextDay);

        panelTaskId.add(panelChangeDayButtons);

        buttonHelp.setText("Help/About");
        buttonHelp.setToolTipText("");
        buttonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHelpActionPerformed(evt);
            }
        });
        panelTaskId.add(buttonHelp);

        panelTop.add(panelTaskId);

        panelEnterTask.setLayout(new java.awt.BorderLayout(10, 0));

        comboBoxTaskId.setEditable(true);
        comboBoxTaskId.setToolTipText("TaskId input field");
        panelEnterTask.add(comboBoxTaskId, java.awt.BorderLayout.CENTER);

        labelTaskId.setText("TaskId");
        panelEnterTask.add(labelTaskId, java.awt.BorderLayout.WEST);

        panelTop.add(panelEnterTask);

        panelButtons.setLayout(new java.awt.GridLayout(1, 4, 10, 10));

        buttonAddTrack.setBackground(new java.awt.Color(51, 255, 51));
        buttonAddTrack.setText("Add track");
        buttonAddTrack.setPreferredSize(new java.awt.Dimension(80, 24));
        buttonAddTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddTrackActionPerformed(evt);
            }
        });
        panelButtons.add(buttonAddTrack);

        buttonPause.setBackground(new java.awt.Color(255, 255, 51));
        buttonPause.setText("Pause");
        buttonPause.setPreferredSize(new java.awt.Dimension(80, 24));
        buttonPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPauseActionPerformed(evt);
            }
        });
        panelButtons.add(buttonPause);

        buttonEndDay.setBackground(new java.awt.Color(255, 102, 102));
        buttonEndDay.setText("End day");
        buttonEndDay.setPreferredSize(new java.awt.Dimension(80, 24));
        buttonEndDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEndDayActionPerformed(evt);
            }
        });
        panelButtons.add(buttonEndDay);

        buttonReport.setText("Report");
        buttonReport.setPreferredSize(new java.awt.Dimension(80, 24));
        buttonReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReportActionPerformed(evt);
            }
        });
        panelButtons.add(buttonReport);

        panelTop.add(panelButtons);

        getContentPane().add(panelTop, java.awt.BorderLayout.NORTH);

        listTracks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listTracks.setComponentPopupMenu(popupMenuTracks);
        listTracks.setMaximumSize(new java.awt.Dimension(380, 0));
        listTracks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listTracksMousePressed(evt);
            }
        });
        scrollPaneTracks.setViewportView(listTracks);

        getContentPane().add(scrollPaneTracks, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAddTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddTrackActionPerformed
        onStart();
    }//GEN-LAST:event_buttonAddTrackActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        onLoad();
    }//GEN-LAST:event_formWindowOpened

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

    private void buttonNextDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextDayActionPerformed
        nextDay();
    }//GEN-LAST:event_buttonNextDayActionPerformed

    private void menuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditActionPerformed
        editTrack();
    }//GEN-LAST:event_menuItemEditActionPerformed

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        deleteTrack();
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void listTracksMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTracksMousePressed
        writeTrack(evt.getClickCount());
    }//GEN-LAST:event_listTracksMousePressed

    private void buttonHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHelpActionPerformed
         viewHelpAboutForm();
    }//GEN-LAST:event_buttonHelpActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        java.awt.EventQueue.invokeLater(() -> {
            MainForm mainForm = new MainForm();
            mainForm.setLocationRelativeTo(null);
            mainForm.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddTrack;
    private javax.swing.JButton buttonEndDay;
    private javax.swing.JButton buttonHelp;
    private javax.swing.JButton buttonNextDay;
    private javax.swing.JButton buttonPause;
    private javax.swing.JButton buttonPrevDay;
    private javax.swing.JButton buttonReport;
    private javax.swing.JComboBox<String> comboBoxTaskId;
    private javax.swing.JLabel labelCurrentDay;
    private javax.swing.JLabel labelTaskId;
    private javax.swing.JList<String> listTracks;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemEdit;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelChangeDayButtons;
    private javax.swing.JPanel panelEnterTask;
    private javax.swing.JPanel panelTaskId;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPopupMenu popupMenuTracks;
    private javax.swing.JScrollPane scrollPaneTracks;
    // End of variables declaration//GEN-END:variables

    private void prevDay() {
        currentDay = currentDay.minusDays(1);
        initSettings();
    }
    
    private void write(TrackItem newTrack) {
        TrackItem last = getLastTrackItem();
        String newTrackId = newTrack.getTrackId();
        if (!last.getTrackId().equals(newTrackId)) {
            if (!Constants.END_DAY.equals(newTrackId)) {
                tracks.removeIf(trackItem -> Constants.END_DAY.equals(trackItem.getTrackId()));
            }
            last = getLastTrackItem();
            if (!last.getTrackId().equals(newTrackId)) {
                addToLastTask(newTrack);
                tracks.add(newTrack);
                persist();
                updateTaskIdCombo();
            }
            listTracks.updateUI();
        }
    }

    private void addToLastTask(TrackItem newTrack) {
            String taskId = newTrack.getTrackId();
        if (!taskId.equalsIgnoreCase(Constants.BEGIN_DAY)
                && !taskId.equalsIgnoreCase(Constants.END_DAY)
                && !taskId.equalsIgnoreCase(Constants.PAUSE)) {
            lastTasks.add(taskId);
        }
    }

    private void updateTaskIdCombo() {
        comboBoxTaskId.setModel(new DefaultComboBoxModel<>(lastTasks.toArray(new String[0])));
        comboBoxTaskId.updateUI();
    }

    private TrackItem getLastTrackItem() {
        TrackItem last;
        if (!tracks.isEmpty()) {
            last = tracks.get(tracks.size() - 1);
        } else {
            last = new TrackItem(Constants.BEGIN_DAY, LocalDateTime.now());
        }
        return last;
    }

    private void writeTrack(int clickCount) {
        int selectedIndex = listTracks.getSelectedIndex();
        if (!tracks.isEmpty() && clickCount == 2 && selectedIndex >= 0
                && selectedIndex < tracks.size()) {
            TrackItem oldTrack = tracks.get(selectedIndex);
            TrackItem newTrack = new TrackItem(oldTrack.getTrackId(), LocalDateTime.now());
            write(newTrack);
        }
    }

    private static LocalTime toDate(long diffInMillis) {
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMillis) % 60;
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
        int hours = (int) TimeUnit.MILLISECONDS.toHours(diffInMillis);
        return LocalTime.of(hours, minutes, seconds);
    }

    private void report() {
        if (tracks.stream()
                  .noneMatch(trackItem -> Constants.END_DAY.equals(trackItem.getTrackId()))) {
            JOptionPane.showMessageDialog(this,
                    "Missing end of day entry, report may be incorrect",
                    "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
        Map<String, ReportItem> tRep = new LinkedHashMap<>();
        for (int i = 1; i < tracks.size(); ++i) {
            TrackItem prev = tracks.get(i - 1);
            TrackItem curr = tracks.get(i);
            String key = prev.getTrackId().replace(" ", "");
            ReportItem value = tRep.get(key);
            long diff = Duration.between(prev.getDate(), curr.getDate()).toMillis();
            if (value == null) {
                tRep.put(key, new ReportItem(prev, diff));
            } else {
                value.increase(diff);
            }
        }
        List<List<String>> repData = new ArrayList<>();
        for (ReportItem reportItem : tRep.values()) {
            long timeDiff = reportItem.getTimeDiff();
            LocalTime localTime = toDate(timeDiff);
            String date = Constants.REPORT_FORMATTER.format(localTime);
            TrackItem trackItem = reportItem.getTrackItem();
            if (!Constants.PAUSE.equals(trackItem.getTrackId())) {
                repData.add(Arrays.asList(trackItem.getTrackId(), date));
            }
        }
        ReportForm reportForm = new ReportForm(this, true);
        reportForm.setLocationRelativeTo(null);
        String currentDateAsString = currentDay.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        reportForm.setTitle("Report for " + currentDateAsString);
        reportForm.setRep(repData);
        reportForm.setVisible(true);
    }

    private void endDay() {
        tracks.removeIf(trackItem -> Constants.END_DAY.equals(trackItem.getTrackId()));
        TrackItem newTrack = new TrackItem(Constants.END_DAY, LocalDateTime.now());
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
    
    private void nextDay() {
        currentDay = currentDay.plusDays(1);
        initSettings();
    }

    private void pause() {
        TrackItem pauseTrack = new TrackItem(Constants.PAUSE, LocalDateTime.now());
        write(pauseTrack);
    }

    private void onLoad() {
        initSettings();
    }

    private void initSettings() {
        if (currentDay.isEqual(today)) {
            buttonAddTrack.setEnabled(true);
            buttonEndDay.setEnabled(true);
            buttonPause.setEnabled(true);
        } else {
            buttonAddTrack.setEnabled(false);
            buttonEndDay.setEnabled(false);
            buttonPause.setEnabled(false);
        }
        String userHome = System.getProperty("user.home");
        String currentDateAsString = currentDay.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        labelCurrentDay.setText("Date: " + currentDateAsString);
        String dirName = userHome + File.separator + "sTimeTracks";
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(dir, "sTime-track-" + currentDateAsString + ".csv");
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
                    TrackItem trackItem = new TrackItem(currentDay, string);
                    tracks.add(trackItem);
                    addToLastTask(trackItem);
                }
                updateTaskIdCombo();
                listTracks.updateUI();
            } catch (ParseException | IOException ex) {
                ex.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    private void onStart() {
        String taskId = String.valueOf(comboBoxTaskId.getSelectedItem());
        if (taskId != null
                && !taskId.isEmpty()
                && !taskId.equalsIgnoreCase(Constants.BEGIN_DAY)
                && !taskId.equalsIgnoreCase(Constants.END_DAY)
                && !taskId.equalsIgnoreCase(Constants.PAUSE)
        ) {
            TrackItem trackItem = new TrackItem(taskId, LocalDateTime.now());
            write(trackItem);
        }
    }
    
    private void editTrack() {
        int selectedIndex = listTracks.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tracks.size()) {
            TrackItem selectedTrack = tracks.get(selectedIndex);
            LocalDateTime prevDate = null;
            if (selectedIndex > 0) {
                TrackItem prevTrack = tracks.get(selectedIndex - 1);
                prevDate = prevTrack.getDate();
            }
            LocalDateTime nextDate = null;
            if (selectedIndex < tracks.size() - 1) {
                TrackItem nextTrack = tracks.get(selectedIndex + 1);
                nextDate = nextTrack.getDate();
            }
            EditTrackItemForm editTrackItemForm = new EditTrackItemForm(this, true);
            editTrackItemForm.setTrack(selectedTrack, prevDate, nextDate);
            editTrackItemForm.setLocationRelativeTo(this);
            editTrackItemForm.setVisible(true);
            TrackItem newTrackItem = editTrackItemForm.getTrackItem();
            if (!selectedTrack.equals(newTrackItem)) {
                tracks.set(selectedIndex, newTrackItem);
                persist();
                listTracks.updateUI();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No items selected", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteTrack() {
        int selectedIndex = listTracks.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tracks.size()) {
            TrackItem selectedTrack = tracks.get(selectedIndex);
            int result = JOptionPane.showConfirmDialog(this, "Delete track item: " + selectedTrack + "?",
                    "Confirm deletion", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                tracks.remove(selectedIndex);
                persist();
                listTracks.updateUI();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No items selected", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void viewHelpAboutForm() {
        JOptionPane.showMessageDialog(this, HELP_ABOUT_TEXT, "About", JOptionPane.INFORMATION_MESSAGE);
    }

}
