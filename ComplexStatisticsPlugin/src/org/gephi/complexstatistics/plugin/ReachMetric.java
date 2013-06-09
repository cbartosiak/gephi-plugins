/*
 * Copyright 2008-2012 Gephi
 * Authors : Cezary Bartosiak
 * Website : http://www.gephi.org
 *
 * This file is part of Gephi.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Gephi Consortium. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 3 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://gephi.org/about/legal/license-notice/
 * or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License files at
 * /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 3, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 3] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 3 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 3 code and therefore, elected the GPL
 * Version 3 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2011 Gephi Consortium.
 */
package org.gephi.complexstatistics.plugin;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.statistics.spi.Statistics;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;

/**
 * 
 *
 * @author Cezary Bartosiak
 */
public class ReachMetric implements Statistics, LongTask {
	private final String COLUMN_NAME = "dset";
	private Boolean cancel = false;
	private ProgressTicket progressTicket;
	private double value = 0.0;

	public void execute(GraphModel graphModel, AttributeModel attributeModel) {
		HierarchicalGraph undirectedGraph = graphModel.getHierarchicalUndirectedGraphVisible();
		execute(undirectedGraph, attributeModel);
	}

	public void execute(HierarchicalGraph graph, AttributeModel attributeModel) {
		HierarchicalGraph undirectedGraph = graph.getView().getGraphModel().getHierarchicalUndirectedGraphVisible();
		calculateValue(undirectedGraph, attributeModel);
	}

	private void calculateValue(HierarchicalGraph graph, AttributeModel attributeModel) {
		cancel = false;

		graph.readLock();

		int n = graph.getNodeCount();
		Progress.start(progressTicket, n + n * n + n * n + n + n + n);

		double[][] d = Utils.floydWarshall(graph, cancel, progressTicket);
		value = Utils.reachMetric(graph, attributeModel, COLUMN_NAME, d, cancel, progressTicket);

		graph.readUnlock();
	}

	public double getValue() {
		return value;
	}

	public String getReport() {
		NumberFormat f = new DecimalFormat("#0.0000");

		String report = "<html><body><h1>Reach Metric Report</h1>"
						+ "<hr>"
						+ "<br>"
						+ "<br><h2>Results:</h2>"
						+ "Reach Metric: " + f.format(value)
						+ "</body></html>";

		return report;
	}

	public boolean cancel() {
		cancel = true;
		return true;
	}

	public void setProgressTicket(ProgressTicket progressTicket) {
		this.progressTicket = progressTicket;
	}
}
