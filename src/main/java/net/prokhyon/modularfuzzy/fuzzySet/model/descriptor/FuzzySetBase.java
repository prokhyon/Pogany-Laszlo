package net.prokhyon.modularfuzzy.fuzzySet.model.descriptor;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import net.prokhyon.modularfuzzy.common.descriptor.FuzzyDescriptorModelBase;

public abstract class FuzzySetBase extends FuzzyDescriptorModelBase {

	@XStreamAsAttribute
	private FuzzySetTypeEnum type;

	@XStreamImplicit
	private List<FuzzyPointBase> points;

	public FuzzySetBase(String id, String label, String description, FuzzySetTypeEnum type, List<FuzzyPointBase> points) {
		super(id, label, description);
		this.type = type;
		this.points = points;
	}

	public FuzzySetTypeEnum getType() {
		return type;
	}

	public void setType(FuzzySetTypeEnum type) {
		this.type = type;
	}

	public List<FuzzyPointBase> getPoints() {
		return points;
	}

	public void setPoints(List<FuzzyPointBase> points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "FuzzySetBase [type=" + type + ", points=" + points + ", id=" + id + ", label=" + label
				+ ", description=" + description + "]";
	}

}
