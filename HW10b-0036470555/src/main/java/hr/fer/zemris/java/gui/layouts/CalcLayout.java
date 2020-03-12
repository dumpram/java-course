package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * CalcLayout is custom java swing layout developed for constructing calculator
 * grids. It has constant dimensions, and constant number of RC blocks. It is
 * forbidden to add more components to one {@link RCPosition}. Forbidden
 * positions are (1,1), (1,2), (1,3), (1,4), (1,5).
 * 
 * @author Ivan Pavić
 * 
 */
public class CalcLayout implements LayoutManager2 {
    /**
     * Private integer representing gap.
     */
    private final int gap;
    /*
     * Map of components.
     */
    private final Map<Component, RCPosition> compTable = new HashMap<>();

    /**
     * Default constructor defines gap 0.
     */
    public CalcLayout() {
	this(0);
    }

    /**
     * Constructor accepts gap. Gap is number of pixels between components.
     * 
     * @param igap
     *            given gap size
     */
    public CalcLayout(int igap) {
	if (igap < 0) {
	    throw new IllegalArgumentException("Gap must be positive");
	}
	gap = igap;
    }

    /**
     * This method is not used in CalcLayout.
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
	compTable.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
	Dimension dimOfRC = getPrefferedRCSize(parent);
	return new Dimension((dimOfRC.width * 7) + gap * 6, (dimOfRC.height)
		* 5 + gap * 4);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
	Dimension dimOfRC = getMinimumRCSize(parent);
	return new Dimension((dimOfRC.width * 7) + gap * 6, (dimOfRC.height)
		* 5 + gap * 4);
    }

    @Override
    public void layoutContainer(Container parent) {
	synchronized (parent.getTreeLock()) {
	    int ncomponents = parent.getComponentCount();

	    if (ncomponents == 0) {
		return;
	    }

	    Dimension size = parent.getSize();

	    Dimension preferred = preferredLayoutSize(parent);

	    Dimension minimum = minimumLayoutSize(parent);

	    Dimension dimOfRC = getPrefferedRCSize(parent);

	    if (size.height < minimum.height) {
		size.height = minimum.height;
	    }
	    if (size.width < minimum.width) {
		size.width = minimum.width;
	    }

	    int heightRC = dimOfRC.height;
	    int widthRC = dimOfRC.width;

	    for (int i = 0; i < ncomponents; i++) {
		heightRC = dimOfRC.height;
		widthRC = dimOfRC.width;
		Component c = parent.getComponent(i);
		RCPosition pos = compTable.get(parent.getComponent(i));
		if (pos == null) {
		    continue;
		}
		widthRC = (int) (widthRC * (1.0 * (size.width - 6 * gap) / (preferred.width - 6 * gap)));
		heightRC = (int) (heightRC * (1.0 * (size.height - 4 * gap) / (preferred.height - 4 * gap)));
		int x = (pos.getColumn() - 1) * (widthRC + gap);
		int y = (pos.getRow() - 1) * (heightRC + gap);
		if (pos.getRow() == 1 && pos.getColumn() == 1) {
		    widthRC = (widthRC) * 5 + 4 * gap;
		}
		c.setBounds(x, y, widthRC, heightRC);
	    }
	}
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
	RCPosition pos = null;
	if (constraints instanceof String) {
	    pos = new RCPosition((String) constraints);
	} else if (constraints instanceof RCPosition) {
	    pos = (RCPosition) constraints;
	} else {
	    throw new IllegalArgumentException(
		    "Invalid constraints format. Check documentation!");
	}
	if (compTable.get(comp) != null) {
	    throw new IllegalArgumentException(
		    "Can't add more elements to one position! Check documentation!");
	}
	compTable.put(comp, pos);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
	return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
	return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
	return 0.5f;
    }

    /**
     * Unimplemented for CalcLayout.
     */
    @Override
    public void invalidateLayout(Container target) {
    }

    /**
     * Gets preferred RC element size. Criteria is that preferred dimensions are
     * maximum width and height of preferred sizes considering all components in
     * given parent container except (1, 1).
     * 
     * @param parent
     *            given parent container
     * @return new Dimension representing preferred RC size
     */
    private Dimension getPrefferedRCSize(Container parent) {
	int maxH = 0;
	int maxW = 0;
	for (int i = 0, len = parent.getComponentCount(); i < len; i++) {
	    Component c = parent.getComponent(i);
	    RCPosition pos = compTable.get(c);
	    if (pos.getRow() == 1 && pos.getColumn() == 1) {
		continue;
	    }
	    if (c.getPreferredSize().height > maxH) {
		maxH = c.getPreferredSize().height;
	    }
	    if (c.getPreferredSize().width > maxW) {
		maxW = c.getPreferredSize().width;
	    }
	}
	return new Dimension(maxW, maxH);
    }

    /**
     * Gets minimal RC element size. Criteria is that minimum dimensions are
     * maximum width and height of minimal dimensions considering all components
     * in container except (1, 1).
     * 
     * @param parent
     *            given container
     * @return Dimension representing minimum RCSize
     */
    private Dimension getMinimumRCSize(Container parent) {
	int maxH = 0;
	int maxW = 0;
	for (int i = 0, len = parent.getComponentCount(); i < len; i++) {
	    Component c = parent.getComponent(i);
	    RCPosition pos = compTable.get(c);
	    if (pos.getRow() == 1 && pos.getColumn() == 1) {
		continue;
	    }
	    if (c.getMinimumSize().height > maxH) {
		maxH = c.getPreferredSize().height;
	    }
	    if (c.getMinimumSize().width > maxW) {
		maxW = c.getPreferredSize().width;
	    }
	}
	return new Dimension(maxW, maxH);
    }
}
