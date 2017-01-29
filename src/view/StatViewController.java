package view;

import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import helpers.DoughnutChart;
import helpers.FileHelper;
import helpers.ProgramPathAndDir;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.MainViewInformaiton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-23
 */
public class StatViewController implements Initializable {
  @FXML
  private Label statLabel;
  @FXML
  private Button statGenBtn;
  @FXML
  private PieChart pieChart;
  private String errorTitle = "Fel i huvudfönster";

  public static void showStatView(String labelID1, Integer stat0To100ID1, String labelID2, Integer stat0To100ID2) {

    if ((stat0To100ID1 + stat0To100ID2) != 100) {
      // ErrorView.showError("fel" , "fel i diagram-indata", "summan blev inte 100", new Exception("summan blev inte 100 : " + stat0To100ID1 +"+" + stat0To100ID2 ));
      stat0To100ID1 = 90;
      stat0To100ID2 = 10;
    }

    PieChart pieChart = new PieChart();
    pieChart.setData(getChartData(labelID1, stat0To100ID1, labelID2, stat0To100ID2));
    pieChart.setTitle("Statistik");
    pieChart.setLegendSide(Side.LEFT);
    pieChart.setClockwise(false);
    pieChart.setLabelsVisible(false);


  }

  private static ObservableList<PieChart.Data> getChartData(String labelID1, Integer stat0To100ID1, String labelID2, Integer stat0To100ID2) {

    ObservableList<PieChart.Data> answer = FXCollections.observableArrayList();
    answer.addAll(new PieChart.Data(labelID1, stat0To100ID1),
        new PieChart.Data(labelID2, stat0To100ID2)
    );
    return answer;
  }

  public static void DoughnutChartView(String labelID1, Integer stat0To100ID1, String labelID2, Integer stat0To100ID2) {

    if ((stat0To100ID1 + stat0To100ID2) != 100) {
      //ErrorView.showError("fel" , "fel i diagram-indata", "summan blev inte 100", new Exception("summan blev inte 100 : " + stat0To100ID1 +"+" + stat0To100ID2 ));
      stat0To100ID1 = 25;
      stat0To100ID2 = 75;
    }
    ObservableList<PieChart.Data> pieChartData = getChartData(labelID1, stat0To100ID1, labelID2, stat0To100ID2);

    final DoughnutChart pieChart = new DoughnutChart(pieChartData);
    pieChart.setTitle("Statistik");
    StackPane root = new StackPane();
    root.getChildren().add(pieChart);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Main.getSpider().setStatViewContrller(this);
    if (Main.getSpider().getMain().getMvi().getCurrentUser().getMemberLevel() != 10) {
      statGenBtn.setVisible(false);
    }
   //StatView.DoughnutChartView("Lediga cyklar",availableBikesStatistic(),"Upptagna cyklar", (100-availableBikesStatistic()) );
    StatView.DoughnutChartView("Lediga cyklar",25,"Upptagna cyklar", (100-25) );
    StatView.showStatView("Lediga cyklar", availableBikesStatistic(), "Upptagna cyklar", (100 - availableBikesStatistic()), pieChart);
  }

  private void updateStatLabel() {
    statLabel.setText(availableBikesStatistic() + "%");
  }

  public float availableBikesStatistic() {
    float part = Main.getSpider().getMain().getMvi().getAvailableBikes();
    float total = Main.getSpider().getMain().getMvi().getTotalBikes();
    float stat;
    try {
      stat = ((part / total) * 100);
    } catch (Exception e) {
      e.printStackTrace();
      ErrorView.showError(errorTitle, "fel vid inläsning av data..", "Kontrollera er data..", null, e);
      stat = 0;
    }
    return stat;
  }

  public void showMainView(Event event) {
    Main.getSpider().getMain().showMainView();
  }

  public void getStatButtonClicked(MouseEvent mouseEvent) {
    //TODO:
    //Get the stat..
    //String fileNameOfPDF = theStats.generatePDFStatsGetFileName();
    getBikesRentStatistic();


  }

  public void getBikesRentStatistic() {
    ServerCall serverCall;
    serverCall = new ServerCallImpl();
    MainViewInformaiton mvi = serverCall.fetchStat();
    if (ChoiceDialogView.showChoiceDialogYESorNO("Temporary save the PDF to " + mvi.getPreferdPdfFileName() + "and open the PDF ?" )) {
      try {
        /*
        //prepere a file
        String fileName = "statPDF" + "" +".pdf";
        String filePath = "c:\\Temp\\"+ fileName;
         */
        FileHelper.saveByteArrayToFile(mvi.getPreferdPdfFileName(), mvi.getPdfStream());
        FileHelper.openPDF(mvi.getPreferdPdfFileName());
        if (ChoiceDialogView.showChoiceDialogYESorNO("Remove the temporary saved file " + mvi.getPreferdPdfFileName() + "?" ))
          ProgramPathAndDir.dumptempfiles(mvi.getPreferdPdfFileName());
      } catch (IOException e) {
        e.printStackTrace();
        ErrorView.showError(errorTitle, "Fel vid inläsning av PDF data..", "Kontakta admin..", null, e);
      }
    }


  }
}
