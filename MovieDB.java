package home.moviedb.mdb;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.border.BevelBorder;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;



public class MovieDB {
	

	private JFrame frmPrivateMovieDatabase;
	private JTextField txtTitle;
	private JTextField txtDirector;
	private JTextField txtLead;
	private JTextField txtSecondary;
	private JTextField txtRunTime;
	private JTextField txtComments;
	private JTextField txtImdbUrl;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JList movieList;
	private Vector<String> titleVec = new Vector<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MovieDB window = new MovieDB();
					window.frmPrivateMovieDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MovieDB() {
		initialize();
		updateMovieList();
	}
	
	private void updateMovieList(){
		DBactions dbConn = DBactions.getInstance(DBConstants.USER, DBConstants.PASS);
		ResultSet result = dbConn.getField("title");
		
		titleVec.clear();


		try {

			String newStr;
			while(result.next()){
				newStr = result.getString("title");
				titleVec.add(newStr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} finally {
			//dbConn.disconnect();
		}
		
		
		movieList.setModel(new AbstractListModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2L;
			
			public int getSize() {
				return titleVec.size();
			}
			public Object getElementAt(int index) {
				return titleVec.get(index);
			}
		});
	}
	
	private void saveAction(ActionEvent arg0){
		MovieDBEntry movie = new MovieDBEntry();
		
		movie.setTitle(txtTitle.getText());
		movie.setDirector(txtDirector.getText());
		movie.setLead(txtLead.getText());
		movie.setSecondary(txtSecondary.getText());
		movie.setRuntime(Integer.parseInt(txtRunTime.getText()));
		movie.setImdburl(txtImdbUrl.getText());
		movie.setComments(txtComments.getText());
		
		DBactions dbConn = DBactions.getInstance(DBConstants.USER, DBConstants.PASS);
		if(dbConn.saveEntry(movie)){
			updateMovieList();
			resetInputboxes();
		} else {
			System.out.println("Save to database failed!");
		}
	}

	private void resetInputboxes(){
		txtTitle.setText("");
		txtDirector.setText("");
		txtLead.setText("");
		txtSecondary.setText("");
		txtRunTime.setText("0");
		txtImdbUrl.setText("www.imdb.com");
		txtComments.setText("");
	}
	
	private void setInputBoxes(MovieDBEntry movie){
		txtTitle.setText(movie.getTitle());
		txtDirector.setText(movie.getDirector());
		txtLead.setText(movie.getLead());
		txtSecondary.setText(movie.getSecondary());
		txtRunTime.setText(Integer.toString(movie.getRuntime()));
		txtImdbUrl.setText(movie.getImdburl());
		txtComments.setText(movie.getComments());
	}
	
	private void movieDBlookup(int listIndex){
		String selectedMovie = movieList.getSelectedValue().toString();
		//String selectedMovie = movieList.getModel().getElementAt(listIndex).toString();
		DBactions dbaction = DBactions.getInstance(DBConstants.USER, DBConstants.PASS);
		MovieDBEntry movie = dbaction.getEntry(selectedMovie);
		
		setInputBoxes(movie);
		
		//System.out.println(selectedMovie);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrivateMovieDatabase = new JFrame();
		frmPrivateMovieDatabase.setResizable(false);
		frmPrivateMovieDatabase.setTitle("Private Movie Database");
		frmPrivateMovieDatabase.setBounds(100, 100, 612, 545);
		frmPrivateMovieDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane m_contentPane = new JSplitPane();
		frmPrivateMovieDatabase.getContentPane().add(m_contentPane, BorderLayout.CENTER);
		
		
		JPanel rightPanel = new JPanel();
		m_contentPane.setRightComponent(rightPanel);
								rightPanel.setLayout(new FormLayout(new ColumnSpec[] {
										FormFactory.MIN_COLSPEC,
										FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
										FormFactory.GROWING_BUTTON_COLSPEC,},
									new RowSpec[] {
										RowSpec.decode("min(200dlu;pref)"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										RowSpec.decode("19px"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										RowSpec.decode("19px"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										RowSpec.decode("19px"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										RowSpec.decode("19px"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										RowSpec.decode("19px"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										RowSpec.decode("19px"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										RowSpec.decode("19px"),
										FormFactory.NARROW_LINE_GAP_ROWSPEC,
										FormFactory.MIN_ROWSPEC,}));
								java.net.URL imageURL = null;
								try {
									imageURL = getClass().getResource("testimage.png");
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								JLabel lblImage;
								try {
									lblImage = new JLabel(new ImageIcon(imageURL));
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									lblImage = new JLabel("Could not find image");
								}
								rightPanel.add(lblImage, "3, 1, center, center");
								
								JLabel lblTitle = new JLabel("Title:");
								rightPanel.add(lblTitle, "1, 3, left, center");
								
								JLabel lblDirector = new JLabel("Director:");
								rightPanel.add(lblDirector, "1, 5, left, center");
								
								JLabel lblLead = new JLabel("Lead:");
								rightPanel.add(lblLead, "1, 7, left, center");
								
								JLabel lblSecondaryCast = new JLabel("Secondary cast:");
								rightPanel.add(lblSecondaryCast, "1, 9, left, center");
								
								JLabel lblRunTime = new JLabel("Run time:");
								rightPanel.add(lblRunTime, "1, 11, left, center");
																		
								JLabel lblImdbUrl = new JLabel("IMDB url");
								rightPanel.add(lblImdbUrl, "1, 13, left, default");
																		
								txtImdbUrl = new JTextField();
								lblImdbUrl.setLabelFor(txtImdbUrl);
								txtImdbUrl.setText("www.imdb.com");
								rightPanel.add(txtImdbUrl, "3, 13, fill, default");
								txtImdbUrl.setColumns(10);
								
								JLabel lblComments = new JLabel("Comments:");
								rightPanel.add(lblComments, "1, 15, left, center");
								
								txtComments = new JTextField();
								lblComments.setLabelFor(txtComments);
								rightPanel.add(txtComments, "3, 15, fill, top");
								txtComments.setColumns(10);
								
								JToolBar toolBar = new JToolBar();
								toolBar.setFloatable(false);
								rightPanel.add(toolBar, "1, 17, 3, 1, left, top");
										
									JButton btnNew = new JButton("New..");		// New Button
									btnNew.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											System.out.println("New button clicked");
										}
									});
									buttonGroup.add(btnNew);
									btnNew.setActionCommand("new");
									toolBar.add(btnNew);
									
									JButton btnDelete = new JButton("Delete"); // Delete Button
									btnDelete.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e){
											System.out.println("Delete button clicked");
										}
									});
									buttonGroup.add(btnDelete);
									btnDelete.setActionCommand("delete");
									toolBar.add(btnDelete);
									
									JButton btnEdit = new JButton("Edit..");
									buttonGroup.add(btnEdit);
									btnEdit.setActionCommand("edit");
									toolBar.add(btnEdit);
									
									JButton btnSave = new JButton("Save");		// Save Button
									btnSave.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											saveAction(arg0);
										}
									});
									buttonGroup.add(btnSave);
									btnSave.setActionCommand("save");
									toolBar.add(btnSave);
									
																				
								txtTitle = new JTextField();
								lblTitle.setLabelFor(txtTitle);
								rightPanel.add(txtTitle, "3, 3, fill, top");
								txtTitle.setColumns(10);
								
								txtDirector = new JTextField();
								lblDirector.setLabelFor(txtDirector);
								rightPanel.add(txtDirector, "3, 5, fill, top");
								txtDirector.setColumns(10);
								
								txtLead = new JTextField();
								lblLead.setLabelFor(txtLead);
								rightPanel.add(txtLead, "3, 7, fill, top");
								txtLead.setColumns(10);
								
								txtSecondary = new JTextField();
								lblSecondaryCast.setLabelFor(txtSecondary);
								rightPanel.add(txtSecondary, "3, 9, fill, top");
								txtSecondary.setColumns(10);
								
								txtRunTime = new JTextField();
								txtRunTime.setText("0");
								lblRunTime.setLabelFor(txtRunTime);
								rightPanel.add(txtRunTime, "3, 11, fill, top");
								txtRunTime.setColumns(10);
																				
							JPanel leftPanel = new JPanel();
							m_contentPane.setLeftComponent(leftPanel);
							leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
								JLabel lblNewLabel = new JLabel("Movies in archive");
								lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
								lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
								leftPanel.add(lblNewLabel);
								
								movieList = new JList(); 
								movieList.addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent event) {
										movieDBlookup(event.getLastIndex());
									}
								});
								movieList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
								movieList.setAlignmentX(Component.LEFT_ALIGNMENT);
								lblNewLabel.setLabelFor(movieList);
								movieList.setVisibleRowCount(5);
								movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								movieList.setModel(new AbstractListModel() {
									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;
									String[] values = new String[] {"Archive empty   "};
									public int getSize() {
										return values.length;
									}
									public Object getElementAt(int index) {
										return values[index];
									}
								});
								movieList.setLayoutOrientation(JList.VERTICAL);
								leftPanel.add(new JScrollPane(movieList));
								


	}
}