package net.prokhyon.modularfuzzy.fuzzySet.model.fx;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import net.prokhyon.modularfuzzy.common.WorkspaceElement;
import net.prokhyon.modularfuzzy.fuzzySet.model.descriptor.FuzzySetSystemTypeEnum;

public class FuzzySetSystem extends WorkspaceElement {

	private final StringProperty uuid;
	private final StringProperty fuzzySystemName;
	private final StringProperty fuzzySystemDescription;
	private final ObjectProperty<FuzzySetSystemTypeEnum> fuzzySystemType;
	private final ListProperty<FuzzySet> fuzzySets;

	private StringProperty initializeUUIDPropertyFromString(String uuid){

		StringProperty fuzzySetUUIDProp = null;
		try{
			String uuidStr = UUID.fromString(uuid).toString();
			fuzzySetUUIDProp = new SimpleStringProperty(uuidStr);
		} catch (Exception exception){
			fuzzySetUUIDProp = new SimpleStringProperty(UUID.randomUUID().toString());
		}
		return fuzzySetUUIDProp;
	}

	public FuzzySetSystem() {
		this("fuzzySystem", "That's a custom fuzzy system", FuzzySetSystemTypeEnum.CUSTOM, null);
	}

	public FuzzySetSystem(String fuzzySystemName, String fuzzySystemDescription, FuzzySetSystemTypeEnum fuzzySystemType,
						  List<FuzzySet> fuzzySets, String uuid) {
		super();
		this.uuid = initializeUUIDPropertyFromString(uuid);
		this.fuzzySystemName = new SimpleStringProperty(fuzzySystemName);
		this.fuzzySystemDescription = new SimpleStringProperty(fuzzySystemDescription);
		this.fuzzySystemType = new SimpleObjectProperty<FuzzySetSystemTypeEnum>(fuzzySystemType);
		if (fuzzySets == null)
			fuzzySets = new ArrayList<FuzzySet>();
		this.fuzzySets = new SimpleListProperty<FuzzySet>(FXCollections.observableArrayList(FuzzySet.extractor()));
	}

	public FuzzySetSystem(String fuzzySystemName, String fuzzySystemDescription, FuzzySetSystemTypeEnum fuzzySystemType,
			List<FuzzySet> fuzzySets) {
		super();
		this.uuid = initializeUUIDPropertyFromString(null);
		this.fuzzySystemName = new SimpleStringProperty(fuzzySystemName);
		this.fuzzySystemDescription = new SimpleStringProperty(fuzzySystemDescription);
		this.fuzzySystemType = new SimpleObjectProperty<FuzzySetSystemTypeEnum>(fuzzySystemType);
		if (fuzzySets == null)
			fuzzySets = new ArrayList<FuzzySet>();
		this.fuzzySets = new SimpleListProperty<FuzzySet>(FXCollections.observableArrayList(FuzzySet.extractor()));
	}

	public String getFuzzySystemName() {
		return fuzzySystemName.get();
	}

	public void setFuzzySystemName(String fuzzySystemName) {
		this.fuzzySystemName.set(fuzzySystemName);
	}

	public StringProperty fuzzySystemNameProperty() {
		return fuzzySystemName;
	}

	public String getFuzzySystemDescription() {
		return fuzzySystemDescription.get();
	}

	public void setFuzzySystemDescription(String fuzzySystemDescription) {
		this.fuzzySystemDescription.set(fuzzySystemDescription);
	}

	public StringProperty fuzzySystemDescriptionProperty() {
		return fuzzySystemDescription;
	}

	public FuzzySetSystemTypeEnum getFuzzySystemType() {
		return fuzzySystemType.get();
	}

	public void setFuzzySystemType(FuzzySetSystemTypeEnum fuzzySystemType) {
		this.fuzzySystemType.set(fuzzySystemType);
	}

	public ObjectProperty<FuzzySetSystemTypeEnum> fuzzySystemTypeProperty() {
		return fuzzySystemType;
	}

	public List<FuzzySet> getFuzzySets() {
		return fuzzySets.get();
	}

	public void setFuzzySets(List<FuzzySet> fuzzySets) {
		this.fuzzySets.set(FXCollections.observableArrayList(fuzzySets));
	}

	public ListProperty<FuzzySet> fuzzySetsProperty() {
		return fuzzySets;
	}

}