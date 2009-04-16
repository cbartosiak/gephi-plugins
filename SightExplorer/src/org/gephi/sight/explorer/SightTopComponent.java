/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.sight.explorer;

import java.io.Serializable;
import java.util.logging.Logger;
import org.gephi.data.network.api.DhnsController;
import org.gephi.data.network.api.SightManager;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
final class SightTopComponent extends TopComponent implements ExplorerManager.Provider, LookupListener {

    private static SightTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "SightTopComponent";
    private final ExplorerManager manager = new ExplorerManager();

    private SightTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(SightTopComponent.class, "CTL_SightTopComponent"));
        setToolTipText(NbBundle.getMessage(SightTopComponent.class, "HINT_SightTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));

        associateLookup(ExplorerUtils.createLookup(manager, getActionMap()));
        Utilities.actionsGlobalContext().lookupResult(Node.class).addLookupListener(this);
        //ExplorerUtils.createLookup(manager, getActionMap()).lookupResult(Node.class).addLookupListener(this);
        initExplorer();


    }

    public void resultChanged(LookupEvent ev) {
        Node[] selectedNodes = manager.getSelectedNodes();
        if (selectedNodes.length > 0 && selectedNodes[0] instanceof SightsNode) {
            SightsNode n = (SightsNode) selectedNodes[0];
            if (n.getSight() != null) {
                DhnsController controller = Lookup.getDefault().lookup(DhnsController.class);
                SightManager sightManager = controller.getSightManager();
                sightManager.selectSight(n.getSight());
            }
        }
    }

    private void initExplorer() {
        manager.setRootContext(new SightsNode(null));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        view = new BeanTreeView();
        //((BeanTreeView)view).setRootVisible(false);

        setLayout(new java.awt.BorderLayout());
        add(view, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane view;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized SightTopComponent getDefault() {
        if (instance == null) {
            instance = new SightTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the SightTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized SightTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SightTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SightTopComponent) {
            return (SightTopComponent) win;
        }
        Logger.getLogger(SightTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    public ExplorerManager getExplorerManager() {
        return manager;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return SightTopComponent.getDefault();
        }
    }
}
