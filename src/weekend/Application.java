package weekend;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Application {

	public final JFrame frame;
	public final JLabel label;
	public final JProgressBar progressbar;
	/** Shows current date and time */
	public final JLabel dateTimeLabel;
	/** Represents current date */
	protected Date date;
	protected SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
	private ProgressbarAction progressbarAction;
	
	public static void main(String... args) {
		var app = new Application();
		app.setupLayout();
		app.createGUI();
		app.setupActions();
		app.show();
	}

	public Application(Date start) {
		this.date = start;
		this.frame = new JFrame("Weekend loaded status");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.label = new JLabel("Weekend has come");
		this.dateTimeLabel = new JLabel(start.toString());
		this.progressbar = new JProgressBar();
	}

	public Application() {
		this(new Date());
	}

	public void setupLayout() {
		var rootPane = new JPanel();
		rootPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.Y_AXIS));
		this.frame.setContentPane(rootPane);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension(screenSize.width / 3, screenSize.height / 3);
		this.frame.setPreferredSize(frameSize);
	}

	public void createGUI() {
		var rootPane = (JPanel) this.frame.getContentPane();
		this.dateTimeLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
		rootPane.add(this.dateTimeLabel);
		this.label.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.progressbar.setMinimum(0);
		this.progressbar.setMaximum(100);
		rootPane.add(this.label);
		rootPane.add(this.progressbar);
	}

	public void show() {
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	public void setupActions() {
		this.setupDateTimeUpdater();
		this.setupProgressbarUpdater();
	}

	private void setupDateTimeUpdater() {
		var timer = new Timer();
		var task = new TimerTask(){
		
			@Override
			public void run() {
				var now = new Date();
				String text = Application.this.dateFormatter.format(now);
				Application.this.dateTimeLabel.setText(text);
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	private void setupProgressbarUpdater() {
		this.progressbarAction = new ProgressbarAction(this);
		this.progressbarAction.start();
	}
}
