/*
 * Copyright 2008-2010 Gephi
 * Authors : Cezary Bartosiak
 * Website : http://www.gephi.org
 * 
 * This file is part of Gephi.
 *
 * Gephi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gephi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.spreadsimulator;

import javax.swing.JPanel;
import org.gephi.spreadsimulator.api.*;
import org.openide.util.lookup.ServiceProvider;

/**
 * 
 *
 * @author Cezary Bartosiak
 */
@ServiceProvider(service = LocationChangeStrategyUI.class)
public class LocationChangeStrategyUIImpl implements LocationChangeStrategyUI {
	private LocationChangeStrategyPanel panel;
	private LocationChangeStrategyImpl lcs;

	@Override
	public JPanel getSettingsPanel() {
		panel = new LocationChangeStrategyPanel();
		return panel;
	}

	@Override
	public void setup(LocationChangeStrategy locationChangeStrategy) {
		lcs = (LocationChangeStrategyImpl)locationChangeStrategy;
		if (panel != null)
			panel.setLocationsProbabilities(lcs.getLocationsProbabilities());
	}

	@Override
	public void unsetup() {
		if (panel != null)
			lcs.setLocationsProbabilities(panel.getLocationsProbabilities());
		panel = null;
		lcs = null;
	}
}
