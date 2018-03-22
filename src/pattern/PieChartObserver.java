package pattern;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class PieChartObserver extends JPanel implements Observer {

    private Vector<CourseRecord> courseData;

    public PieChartObserver(CourseData data){
        data.attach(this);
        this.courseData = data.getUpdate();
        this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
                + (LayoutConstants.barSpacing + LayoutConstants.barWidth)
                * this.courseData.size(), LayoutConstants.graphHeight + 2
                * LayoutConstants.yOffset));
        this.setBackground(Color.white);
    }

    @Override
    public void update(Observable o) {
        CourseData data = (CourseData) o;
        this.courseData = data.getUpdate();
        this.revalidate();
        this.repaint();

    }

    public double getTotal(Vector<CourseRecord> data){
        double total = 0.0;
        for (int i = 0; i < data.size(); i++) {
            total += data.get(i).getNumOfStudents();
        }

        return total;
    }

    public void paint(Graphics g) {
        super.paint(g);
        LayoutConstants.paintBarChartOutline(g, this.courseData.size());
        g.setColor(Color.blue);
        double total = getTotal(courseData);
        int radius = 100;
        double startAngle = 0.0;
        for (int i = 0; i < courseData.size(); i++) {
            CourseRecord record = (CourseRecord) courseData.elementAt(i);
            double ratio = (record.getNumOfStudents() / total) * 360.0;
            //draw the arc
            g.setColor(LayoutConstants.courseColours[i% LayoutConstants.courseColours.length]);
            g.fillArc(LayoutConstants.xOffset, LayoutConstants.yOffset, 2 * radius, 2 * radius, (int) startAngle, (int) ratio);
            startAngle += ratio;
        }
    }
}
