package net.prokhyon.modularfuzzy.fuzzySet.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import net.prokhyon.modularfuzzy.api.LoadableDataController;
import net.prokhyon.modularfuzzy.common.CommonServices;
import net.prokhyon.modularfuzzy.common.WorkspaceElement;
import net.prokhyon.modularfuzzy.fuzzySet.model.FuzzySet;
import net.prokhyon.modularfuzzy.fuzzySet.model.FuzzySetPoint;
import net.prokhyon.modularfuzzy.fuzzySet.model.FuzzySetSystem;
import net.prokhyon.modularfuzzy.fuzzySet.model.descriptor.FuzzySetSystemTypeEnum;
import net.prokhyon.modularfuzzy.fuzzySet.model.descriptor.FuzzySetTypeEnum;
import net.prokhyon.modularfuzzy.fuzzySet.util.ExtendedNumberStringConverter;
import net.prokhyon.modularfuzzy.fuzzySet.view.drawing.DrawHelper;
import net.prokhyon.modularfuzzy.shell.services.ServiceFactory;

public class FuzzySetEditorController implements LoadableDataController {

	private ObjectProperty<FuzzySetSystem> fuzzySystem;

	private ObjectProperty<FuzzySet> fuzzySetToEdit;

	@FXML
	private Pane fuzzySetSystemPane;

	@FXML
	private TextField systemNameTextField;

	@FXML
	private ComboBox<FuzzySetSystemTypeEnum> fuzzySetSystemTypeComboBox;

	@FXML
	private TextArea systemDescriptionTextArea;

	@FXML
	private Button createSystemButton;

	@FXML
	private Button clearSystemButton;

	@FXML
	private Button saveSystemButton;

	@FXML
	private ListView<FuzzySet> fuzzySetListView;

	@FXML
	private Button createSetButton;

	@FXML
	private Button editSetButton;

	@FXML
	private Button deleteSetButton;

	@FXML
	private Button saveSetButton;

	@FXML
	private TextField setNameTextField;

	@FXML
	private ComboBox<FuzzySetTypeEnum> fuzzySetTypeComboBox;

	@FXML
	private TextArea setDescriptionTextArea;

	@FXML
	private TableView<FuzzySetPoint> pointsTableView;

	@FXML
	private TableColumn<FuzzySetPoint, Number> xCoordinateColumn;

	@FXML
	private TableColumn<FuzzySetPoint, Number> yCoordinateColumn;

	@FXML
	private Spinner<Double> xCoordinateSpinner;

	@FXML
	private Spinner<Double> yCoordinateSpinner;

	@FXML
	private Button addPointButton;

	@FXML
	private Button deletePointButton;

	private int createdSetCounter;

	@FXML
	private void initialize() {

		this.fuzzySystem = new SimpleObjectProperty<FuzzySetSystem>();
		this.fuzzySetToEdit = new SimpleObjectProperty<FuzzySet>();

		BooleanBinding isNotLoadedSetSystem = Bindings.isNull(fuzzySystem);
		BooleanBinding isSetSelectedForEditing = fuzzySetToEdit.isNotNull();
		BooleanBinding isNotSetSelectedForEditing = Bindings.isNull(fuzzySetToEdit);
		BooleanBinding editingSetMode = Bindings.isNull(fuzzySetListView.getSelectionModel().selectedItemProperty())
				.or(isSetSelectedForEditing);

		createSystemButton.disableProperty().bind(isSetSelectedForEditing);
		clearSystemButton.disableProperty().bind(isNotLoadedSetSystem.or(isSetSelectedForEditing));
		saveSystemButton.disableProperty().bind(isNotLoadedSetSystem.or(isSetSelectedForEditing));
		systemNameTextField.disableProperty().bind(isNotLoadedSetSystem.or(isSetSelectedForEditing));
		fuzzySetSystemTypeComboBox.disableProperty().bind(isNotLoadedSetSystem.or(isSetSelectedForEditing));
		systemDescriptionTextArea.disableProperty().bind(isNotLoadedSetSystem.or(isSetSelectedForEditing));
		fuzzySetListView.disableProperty().bind(isNotLoadedSetSystem.or(isSetSelectedForEditing));
		createSetButton.disableProperty().bind(isNotLoadedSetSystem.or(isSetSelectedForEditing));
		editSetButton.disableProperty().bind(editingSetMode);
		deleteSetButton.disableProperty().bind(editingSetMode);
		saveSetButton.disableProperty().bind(isNotSetSelectedForEditing);

		setNameTextField.disableProperty().bind(isNotSetSelectedForEditing);
		fuzzySetTypeComboBox.disableProperty().bind(isNotSetSelectedForEditing);
		setDescriptionTextArea.disableProperty().bind(isNotSetSelectedForEditing);
		xCoordinateSpinner.disableProperty().bind(isNotSetSelectedForEditing);
		yCoordinateSpinner.disableProperty().bind(isNotSetSelectedForEditing);
		pointsTableView.disableProperty().bind(isNotSetSelectedForEditing);

		addPointButton.disableProperty().bind(isNotSetSelectedForEditing);
		deletePointButton.disableProperty().bind(isNotSetSelectedForEditing);

		xCoordinateSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0, 0.01));
		yCoordinateSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0, 0.01));

		fuzzySetListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			pointsTableView.itemsProperty().unbind();
			pointsTableView.itemsProperty().setValue(null);
			if (newSelection != null) {
				pointsTableView.itemsProperty().bind(newSelection.fuzzySetPointsProperty());
			}
		});

		xCoordinateColumn.setCellValueFactory(cellData -> cellData.getValue().xPointProperty());
		xCoordinateColumn.setCellFactory(
				TextFieldTableCell.<FuzzySetPoint, Number> forTableColumn(new ExtendedNumberStringConverter()));
		yCoordinateColumn.setCellValueFactory(cellData -> cellData.getValue().yPointProperty());
		yCoordinateColumn.setCellFactory(
				TextFieldTableCell.<FuzzySetPoint, Number> forTableColumn(new ExtendedNumberStringConverter()));

		DrawHelper.initialize(fuzzySetSystemPane, fuzzySystem);
	}

	@FXML
	private void createFuzzySetSystem() {

		loadWithData(null);
	}

	@Override
	public <T extends WorkspaceElement> void loadWithData(T modelToLoad) {

		if (modelToLoad == null)
			this.fuzzySystem.set(new FuzzySetSystem());
		else
			this.fuzzySystem.set((FuzzySetSystem) modelToLoad);

		systemNameTextField.textProperty().bindBidirectional(fuzzySystem.get().fuzzySystemNameProperty());
		systemDescriptionTextArea.textProperty().bindBidirectional(fuzzySystem.get().fuzzySystemDescriptionProperty());
		fuzzySetSystemTypeComboBox.setItems(FXCollections.observableArrayList(FuzzySetSystemTypeEnum.values()));
		fuzzySetSystemTypeComboBox.valueProperty().bindBidirectional(fuzzySystem.get().fuzzySystemTypeProperty());
		fuzzySetListView.itemsProperty().bindBidirectional(fuzzySystem.get().fuzzySetsProperty());

		DrawHelper.drawFuzzySystem();
		createdSetCounter = 0;

	}

	@FXML
	private void clearFuzzySetSystem() {

		if (fuzzySystem.getValue() != null) {
			systemNameTextField.textProperty().unbindBidirectional(fuzzySystem.getValue().fuzzySystemNameProperty());
			fuzzySetSystemTypeComboBox.valueProperty().unbindBidirectional(fuzzySystem.get().fuzzySystemTypeProperty());
			systemDescriptionTextArea.textProperty()
					.unbindBidirectional(fuzzySystem.get().fuzzySystemDescriptionProperty());
			fuzzySetListView.itemsProperty().unbindBidirectional(fuzzySystem.get().fuzzySetsProperty());
		}
		fuzzySystem.setValue(null);

		systemNameTextField.textProperty().set(null);
		fuzzySetSystemTypeComboBox.getItems().clear();
		systemDescriptionTextArea.textProperty().set(null);

		fuzzySetListView.getSelectionModel().clearSelection();
		fuzzySetListView.getItems().clear();
		fuzzySetListView.itemsProperty().setValue(null);

		DrawHelper.clearPane();
	}

	@FXML
	private void saveSystem() {

		CommonServices services = new ServiceFactory().getCommonServices();

		FuzzySetSystem fuzzySetSystem = fuzzySystem.get();
		if (fuzzySetSystem != null) {
			services.addModelStore(fuzzySetSystem);
		}
	}

	@FXML
	private void createSet() {

		createdSetCounter++;
		fuzzySystem.get().fuzzySetsProperty()
				.add(new FuzzySet("set" + createdSetCounter, null, FuzzySetTypeEnum.TRIANGULAR, null));
		DrawHelper.drawFuzzySystem();
	}

	@FXML
	private void editSet() {

		ObservableList<FuzzySet> selectedItems = fuzzySetListView.getSelectionModel().getSelectedItems();
		if (selectedItems.size() != 1)
			return;

		fuzzySetToEdit.setValue(selectedItems.get(0));
		setNameTextField.textProperty().bindBidirectional(fuzzySetToEdit.get().fuzySetNameProperty());
		fuzzySetTypeComboBox.setItems(FXCollections.observableArrayList(FuzzySetTypeEnum.values()));
		fuzzySetTypeComboBox.valueProperty().bindBidirectional(fuzzySetToEdit.get().fuzzySetTypeProperty());
		setDescriptionTextArea.textProperty().bindBidirectional(fuzzySetToEdit.get().fuzzySetDescriptionProperty());
		DrawHelper.drawFuzzySystem();
	}

	@FXML
	private void deleteSet() {

		ObservableList<FuzzySet> selectedItems = fuzzySetListView.getSelectionModel().getSelectedItems();
		fuzzySystem.get().fuzzySetsProperty().removeAll(selectedItems);
		DrawHelper.drawFuzzySystem();
	}

	@FXML
	private void saveSet() {

		setNameTextField.textProperty().unbindBidirectional(fuzzySetToEdit.get().fuzySetNameProperty());
		fuzzySetTypeComboBox.valueProperty().unbindBidirectional(fuzzySetToEdit.get().fuzzySetTypeProperty());
		setDescriptionTextArea.textProperty().unbindBidirectional(fuzzySetToEdit.get().fuzzySetDescriptionProperty());

		fuzzySetToEdit.setValue(null);

		setNameTextField.textProperty().set(null);
		fuzzySetTypeComboBox.getItems().clear();
		setDescriptionTextArea.textProperty().set(null);
		DrawHelper.drawFuzzySystem();
	}

	@FXML
	private void addPoint() {

		fuzzySetToEdit.get().fuzzySetPointsProperty().add(new FuzzySetPoint(xCoordinateSpinner.getValue().floatValue(),
				yCoordinateSpinner.getValue().floatValue()));
		DrawHelper.drawFuzzySystem();
	}

	@FXML
	private void deletePoint() {

		fuzzySetToEdit.get().fuzzySetPointsProperty().removeAll(pointsTableView.getSelectionModel().getSelectedItems());
		DrawHelper.drawFuzzySystem();
	}

}