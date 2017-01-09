package view;

import helpers.DoughnutChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;

/**
 * @author Ulrika Goloconda Fahlén, Niklas Karlsson
 * @version 1.0
 * @since 2016-09-22
 */

public class StatView {


  /**
   * Created by NIK1114 on 2016-09-18.
   * <p>
   * StatView.showStatView("labelID1", 50, "labelID2", 50);
   * or
   * StatView.DoughnutChartView("labelID1", 50, "labelID2", 50);
   */

  public static void showStatView(String labelID1, float stat0To100ID1, String labelID2, float stat0To100ID2, PieChart pieChart) {

    if ((stat0To100ID1 + stat0To100ID2) != 100) {
      // ErrorView.showError("fel" , "fel i diagram-indata", "summan blev inte 100", new Exception("summan blev inte 100 : " + stat0To100ID1 +"+" + stat0To100ID2 ));
      stat0To100ID1 = 90;
      stat0To100ID2 = 10;
    }
    pieChart.setData(getChartData(labelID1, stat0To100ID1, labelID2, stat0To100ID2));
    pieChart.setTitle("Statistik");
    pieChart.setLegendSide(Side.LEFT);
    pieChart.setClockwise(false);
    pieChart.setLabelsVisible(false);


  }

  private static ObservableList<PieChart.Data> getChartData(String labelID1, float stat0To100ID1, String labelID2, float stat0To100ID2) {

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

}

