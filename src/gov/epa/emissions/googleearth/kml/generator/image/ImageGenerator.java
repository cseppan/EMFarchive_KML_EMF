package gov.epa.emissions.googleearth.kml.generator.image;

import gov.epa.emissions.googleearth.kml.generator.BinnedPointSourceGenerator;

import java.io.File;
import java.util.ArrayList;

public interface ImageGenerator {

	void generateImages(BinnedPointSourceGenerator generator);

	boolean shouldDrawTitleLegend();

	ArrayList<File> getImages();

	File getLegend();

	File getTitleLegend();

	File getStatsLegend();
}
