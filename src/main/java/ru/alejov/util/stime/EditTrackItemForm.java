package ru.alejov.util.stime;

import javax.swing.JOptionPane;
import java.awt.HeadlessException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * @author Ovcharov Alexey
 */
public class EditTrackItemForm extends javax.swing.JDialog {

    private TrackItem trackItem;
    private LocalDateTime prevDate;
    private LocalDateTime nextDate;
    private LocalDate currentDate;

    /**
     * Creates new form EditTrackItemForm
     * @param parent
     * @param modal
     */
    public EditTrackItemForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        labelTaskId = new javax.swing.JLabel();
        textFieldTaskId = new javax.swing.JTextField();
        labelTime = new javax.swing.JLabel();
        textFieldTime = new javax.swing.JTextField();
        panelButtons = new javax.swing.JPanel();
        buttonOk = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(300, 250));

        panelMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));
        panelMain.setLayout(new java.awt.GridLayout(5, 1, 10, 10));

        labelTaskId.setText("TaskId");
        panelMain.add(labelTaskId);
        panelMain.add(textFieldTaskId);

        labelTime.setText("Time");
        panelMain.add(labelTime);
        panelMain.add(textFieldTime);

        panelButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 2, 0));
        panelButtons.setLayout(new java.awt.GridLayout(1, 2, 10, 10));

        buttonOk.setText("OK");
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });
        panelButtons.add(buttonOk);

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        panelButtons.add(buttonCancel);

        panelMain.add(panelButtons);

        getContentPane().add(panelMain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        completeEdit();
    }//GEN-LAST:event_buttonOkActionPerformed

    private void completeEdit() throws HeadlessException {
        String text = textFieldTime.getText();
        try {
            String trackId = textFieldTaskId.getText();
            if (!trackId.isEmpty() && !Constants.BEGIN_DAY.equals(trackId)
                && !Constants.PAUSE.equals(trackId)
                && !Constants.END_DAY.equals(trackId)) {
                LocalTime localTime = LocalTime.parse(text, Constants.TIME_FORMATTER);
                LocalDateTime date = LocalDateTime.of(currentDate, localTime);
                if ((prevDate == null || date.isAfter(prevDate))
                    && (nextDate == null || date.isBefore(nextDate))) {
                    trackItem = new TrackItem(trackId, date);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Time value intersects with existing intervals");
                }
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid time: " + text);
        }
    }
    

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOk;
    private javax.swing.JLabel labelTaskId;
    private javax.swing.JLabel labelTime;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelMain;
    private javax.swing.JTextField textFieldTaskId;
    private javax.swing.JTextField textFieldTime;
    // End of variables declaration//GEN-END:variables

    void setTrack(TrackItem selectedTrack, LocalDateTime prevDate, LocalDateTime nextDate) {
        this.prevDate = prevDate;
        this.nextDate = nextDate;
        LocalDateTime trackDate = selectedTrack.getDate();
        this.currentDate = trackDate.toLocalDate();
        String trackId = selectedTrack.getTrackId();
        textFieldTime.setText(Constants.TIME_FORMATTER.format(trackDate));
        textFieldTaskId.setText(trackId);
    }

    TrackItem getTrackItem() {
        return trackItem;
    }

}
