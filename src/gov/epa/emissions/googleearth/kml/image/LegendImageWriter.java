package gov.epa.emissions.googleearth.kml.image;

import gov.epa.emissions.googleearth.kml.bin.Range;
import gov.epa.emissions.googleearth.kml.generator.BinRangeManager;
import gov.epa.emissions.googleearth.kml.generator.BinRangeManagerImpl;
import gov.epa.emissions.googleearth.kml.utils.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

public class LegendImageWriter {

	private BufferedImage bufferedImage;
	private int size = 16;

	public LegendImageWriter() {
	}

	public void writeImage(File outputFile) throws IOException {
		ImageIO.write(this.bufferedImage, "png", outputFile);
	}

	public void drawImage(List<Color> colors, BinRangeManager binRangeManager) {

		Collections.reverse(colors);

		this.bufferedImage = new BufferedImage(2 * size + 145, 3 * size
				* colors.size() / 2, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = (Graphics2D) this.bufferedImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, this.bufferedImage.getWidth() - 1, this.bufferedImage
				.getHeight() - 1);

		g.setColor(Color.WHITE);
		g.fillRect(2, 2, this.bufferedImage.getWidth() - 5, this.bufferedImage
				.getHeight() - 5);

		int i = 0;
		for (Color color : colors) {

			g.setColor(color);
			g.fillOval(1 + size / 4, 1 + size / 4 + 3 * size * i / 2,
					this.size - 2, this.size - 2);
			i++;
		}

		int tab1 = 3 * size / 2;
		int tab2 = tab1 + 8;
		int tab3 = tab2 + 59;
		int tab4 = tab3 + 14;

		g.setColor(Color.BLACK);

		int k = 0;
		for (int j = binRangeManager.getRangeCount() - 1; j >= 0; j--) {

			Range range = binRangeManager.getRange(j);
			
			double minRange = binRangeManager.getMinRange();
			
			String minStr = Utils.format(range.getMin(), Utils.getFormat(minRange));
			String maxStr = Utils.format(range.getMax(), Utils.getFormat(minRange));
			//String minStr = Utils.format(range.getMin(), Utils.getFormat(range
			//		.getMin()));
			//String maxStr = Utils.format(range.getMax(), Utils.getFormat(range
			//		.getMax()));			

			if (range.getMin() < 0) {

				g.drawString(maxStr, tab2, size + 3 * size * k / 2);
				g.drawString("to", tab3, size + 3 * size * k / 2);
				g.drawString(minStr, tab4, size + 3 * size * k / 2);
			} else {

				g.drawString(minStr, tab2, size + 3 * size * k / 2);
				g.drawString("to", tab3, size + 3 * size * k / 2);
				g.drawString(maxStr, tab4, size + 3 * size * k / 2);
			}

			k++;
		}
	}

	public static void main(String[] args) throws IOException {

		LegendImageWriter legendWriter = new LegendImageWriter();

		List<Range> ranges = new ArrayList<Range>();

		double min = 1.234;
		double max = 1.235;
		Range range = new Range();
		range.setMin(min);
		range.setMax(max);
		ranges.add(range);

		min = max;
		max = min + .001;
		range = new Range();
		range.setMin(min);
		range.setMax(max);
		ranges.add(range);

		min = max;
		max = min + .001;
		range = new Range();
		range.setMin(min);
		range.setMax(max);
		ranges.add(range);

		min = max;
		max = min + .001;
		range = new Range();
		range.setMin(min);
		range.setMax(max);
		ranges.add(range);

		min = max;
		max = min + .001;
		range = new Range();
		range.setMin(min);
		range.setMax(max);
		ranges.add(range);

		min = max;
		max = min + .001;
		range = new Range();
		range.setMin(min);
		range.setMax(max);
		ranges.add(range);

		legendWriter.drawImage(Arrays
				.asList(new Color[] { Color.BLUE, Color.CYAN, Color.GREEN,
						Color.YELLOW, Color.ORANGE, Color.RED }),
				new BinRangeManagerImpl(ranges));
		legendWriter.writeImage(new File("./legend.png"));
	}
}