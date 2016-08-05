package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import org.apache.log4j.Logger;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import beans.ProductDetail;


public class ProductInformationWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel dataContentPanel;
	private static final Logger productDetailScreenLogger = Logger.getLogger(ProductInformationWindow.class);
	private ArrayList<ProductDetail> dataContent = new ArrayList<>();
	private JSplitPane splitPane;
	private JMenuBar menuBar;
	private JMenu utilitiesMenu;
	private JButton uploadButton;
	private JScrollPane masterScrollPane;
	private JScrollPane detailScrollPane;
	@SuppressWarnings("rawtypes")
	private JList masterNameList ;
	private JButton closeButton;
	private static ProductInformationWindow frame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ProductInformationWindow();
					frame.init();
					frame.setVisible(true);
					frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * init the frame.
	 * @return 
	 */
	public void init() {
		setTitle("Product Information Window");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		utilitiesMenu = new JMenu("Utilities");
		menuBar.add(utilitiesMenu);

		masterScrollPane = new JScrollPane();
		masterScrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 3));
		detailScrollPane = new JScrollPane();
		detailScrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 3));
		uploadButton = new JButton("Upload...");
		//Upload the Input File to memory
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileSelect = new JFileChooser();
				int selectCode = fileSelect.showOpenDialog(ProductInformationWindow.this);
				if (selectCode == JFileChooser.APPROVE_OPTION) {
					File file = fileSelect.getSelectedFile();
					//ignore any non csv file.
					if(file != null && file.exists() && file.getName().endsWith(".csv")){
						productDetailScreenLogger.error("Selected File: " + file.getName());
						//parse CSV File and load it in memory
						readAndStoreCSVDataInMemory(file);
					}
					else {
						productDetailScreenLogger.error("Selected File: " + file.getName() + " is not csv");
						return;
					}
				}
				populateMasterScrollPaneView();
			}
		});

		utilitiesMenu.add(uploadButton);
		
		closeButton = new JButton("Close Window...");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frame.isDisplayable()) {
	                frame.dispose();
	            }
			}
		});
		utilitiesMenu.add(closeButton);
		dataContentPanel = new JPanel();
		dataContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(dataContentPanel);
		dataContentPanel.setLayout(new BorderLayout(0, 0));


		//Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				masterScrollPane, detailScrollPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		masterScrollPane.setMinimumSize(minimumSize);
		detailScrollPane.setMinimumSize(minimumSize);
		dataContentPanel.add(splitPane);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void populateMasterScrollPaneView() {
		ArrayList<String> nameList = new ArrayList<>();

		for(ProductDetail eachProd : dataContent){
			nameList.add(eachProd.getProductName().trim());
		}
		masterNameList = new JList(nameList.toArray());
		ListSelectionModel listSelectionModel = masterNameList.getSelectionModel();
		listSelectionModel.addListSelectionListener(
				new DetailListSelectionHandler());
		masterScrollPane.setViewportView(masterNameList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
	private void readAndStoreCSVDataInMemory(File inputCSVFile) {
		CSVReader csvReader = null;
		dataContent = new ArrayList<>();
		try {
			csvReader = new CSVReader(new FileReader(inputCSVFile));
			ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
			mappingStrategy.setType(ProductDetail.class);
			String[] fields = new String[] {"productId", "productName", "productDescription"}; // the fields to bind do in your JavaBean
			mappingStrategy.setColumnMapping(fields);

			CsvToBean csv = new CsvToBean();
			dataContent = (ArrayList<ProductDetail>) csv.parse(mappingStrategy, csvReader);
			productDetailScreenLogger.info("Read Data from " + inputCSVFile.getName() + ",Total Records:" + dataContent.size());
		} catch (FileNotFoundException e) {
			productDetailScreenLogger.error(e);
		} catch (IOException e) {
			productDetailScreenLogger.error(e);
		}
	}

	private class DetailListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel masterItem = (ListSelectionModel)e.getSource();
			String[] detailData = {"Product Name", "Product Description"}; 
			JTable dataTable = new JTable(1, 2);
			for(int i=0;i<dataTable.getColumnCount();i++)
			{
			TableColumn eachCol = dataTable.getTableHeader().getColumnModel().getColumn(i);
			  
			eachCol.setHeaderValue(detailData[i]);
			} 
			ProductDetail _product = dataContent.get(masterItem.getAnchorSelectionIndex());
			dataTable.setValueAt(_product.getProductName(),0,0);
			dataTable.setValueAt(_product.getProductDescription(),0,1);
			detailScrollPane.setViewportView(dataTable);
		}
	}
}
