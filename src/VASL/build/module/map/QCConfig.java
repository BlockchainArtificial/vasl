/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VASL.build.module.map;

import VASSAL.build.module.map.PieceMover;
import VASSAL.tools.image.ImageUtils;
import VASSAL.tools.imageop.Op;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

//class QCTreeCellRenderer implements TreeCellRenderer {
class QCTreeCellRenderer extends DefaultTreeCellRenderer {

    QCTreeCellRenderer() {}

    public BufferedImage CreateIcon(BufferedImage objCounterIcon) 
    {
        final int l_iBorder = 3;
        final int l_iSizeOrigin = objCounterIcon.getWidth();
        final int l_iSize = l_iSizeOrigin + 2 * l_iBorder;
        final BufferedImage l_objBI = ImageUtils.createCompatibleTranslucentImage(l_iSize, l_iSize);
        final Graphics2D l_objGraphics = l_objBI.createGraphics();

        l_objGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        l_objGraphics.setColor(Color.WHITE);
        l_objGraphics.fillRect(0, 0, l_iSize, l_iSize);            
        l_objGraphics.setColor(Color.BLACK);
        l_objGraphics.drawRect(l_iBorder - 1, l_iBorder - 1, l_iSizeOrigin + 1, l_iSizeOrigin + 1);

        l_objGraphics.drawImage(objCounterIcon, l_iBorder, l_iBorder, null);

        l_objGraphics.dispose();

        return l_objBI;        
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) 
    {
        JLabel l_objLabel = (JLabel)super.getTreeCellRendererComponent(
                            tree, value, selected,
                            expanded, leaf, row,
                            hasFocus); 
   
        DefaultMutableTreeNode l_objObject = ((DefaultMutableTreeNode) value);
        
        if (l_objObject instanceof QCConfiguration) 
        {
            QCConfiguration l_objQCConfiguration = (QCConfiguration) l_objObject;
            //m_objLabel.setIcon(new ImageIcon(country.getFlagIcon()));
            l_objLabel.setIcon(null);
            l_objLabel.setText(l_objQCConfiguration.getDescription());
        } 
        else if (l_objObject instanceof QCConfigurationEntry) 
        {
            QCConfigurationEntry l_objConfigurationEntry = (QCConfigurationEntry) l_objObject;
            
            if (l_objConfigurationEntry.isMenu())
            {
                l_objLabel.setIcon(new ImageIcon(CreateIcon(l_objConfigurationEntry.CreateButtonMenuIcon())));
                
                if (l_objConfigurationEntry.getText() != null)
                    l_objLabel.setText(l_objConfigurationEntry.getText());
                else
                    l_objLabel.setText("submenu");
            }
            else
            {
                if (l_objConfigurationEntry.getPieceSlot() != null)
                {
                    l_objLabel.setIcon(new ImageIcon(CreateIcon(l_objConfigurationEntry.CreateButtonIcon())));
                    l_objLabel.setText(l_objConfigurationEntry.getPieceSlot().getPiece().getName());
                }
                else
                {
                    BufferedImage l_objUnknown = null;
                    try
                    {
                        l_objUnknown = Op.load("QC/unknown.png").getImage();
                    }
                    catch (Exception ex) 
                    {
                        ex.printStackTrace();
                    }
                    
                    if (l_objUnknown != null)
                        l_objLabel.setIcon(new ImageIcon(CreateIcon(l_objUnknown)));
                    else
                        l_objLabel.setIcon(null);
                    
                    l_objLabel.setText("Unknown counter");
                }
            }
        }
        
        return l_objLabel;
    }
}

/**
 *
 * @author Federico
 */
public class QCConfig 
{
    private QCConfiguration m_objConfiguration;
    private QCConfiguration m_objWorkingConfiguration;
    private JFrame m_objFrame;
    final private QCConfig m_objSelf = this;
    private DefaultMutableTreeNode m_objRootNode;
    DefaultTreeModel m_objModelTree;
    
    public QCConfig() 
    {
        m_objConfiguration  = null;
        m_objWorkingConfiguration = null;
        m_objFrame = null;       
        m_objRootNode = null;
        m_objModelTree = null;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        QCConfigTB = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        QCSP = new javax.swing.JScrollPane();
        
        m_objModelTree = new DefaultTreeModel(m_objWorkingConfiguration);
        QCTree = new javax.swing.JTree(m_objModelTree);
        QCTree.setCellRenderer(new QCTreeCellRenderer());
        ((BasicTreeUI)QCTree.getUI()).setLeftChildIndent(20);        
    
        expandAll(QCTree);
        
        QCConfigTB.setFloatable(false);
        QCConfigTB.setOrientation(javax.swing.SwingConstants.VERTICAL);
        QCConfigTB.setRollover(true);
        QCConfigTB.setMaximumSize(new java.awt.Dimension(64, 23));
        QCConfigTB.setMinimumSize(new java.awt.Dimension(64, 23));
        QCConfigTB.setPreferredSize(new java.awt.Dimension(64, 23));

        jButton5.setText("jButton5");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        QCConfigTB.add(jButton5);

        QCSP.setName(""); // NOI18N
        QCSP.setPreferredSize(new java.awt.Dimension(300, 500));
        QCSP.setRequestFocusEnabled(false);

        QCTree.setShowsRootHandles(true);
        QCTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        QCSP.setViewportView(QCTree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(m_objFrame.getContentPane());
        m_objFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(QCSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QCConfigTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QCConfigTB, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
            .addComponent(QCSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        QCSP.getAccessibleContext().setAccessibleName("");

        m_objFrame.pack();
    }// </editor-fold>                        
    
    public void expandAll(JTree objTree) 
    {
        TreeNode objRootNode = (TreeNode) objTree.getModel().getRoot();
        
        expandAll(objTree, new TreePath(objRootNode));
    }

    private void expandAll(JTree objTree, TreePath objParentPath) 
    {
        TreeNode objNode = (TreeNode) objParentPath.getLastPathComponent();

        if (objNode.getChildCount() >= 0) 
        {
            for (Enumeration e = objNode.children(); e.hasMoreElements();) 
            {
              TreeNode n = (TreeNode) e.nextElement();
              TreePath path = objParentPath.pathByAddingChild(n);
              expandAll(objTree, path);
            }
        }
        
        objTree.expandPath(objParentPath);
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JToolBar QCConfigTB;
    private javax.swing.JScrollPane QCSP;
    private javax.swing.JTree QCTree;
    private javax.swing.JButton jButton5;
    // End of variables declaration                   

    /**
     * @return the m_objConfiguration
     */
    public QCConfiguration getConfiguration() 
    {
        return m_objConfiguration;
    }

    /**
     * @param m_objConfiguration the m_objConfiguration to set
     */
    public void setConfiguration(QCConfiguration objConfiguration) 
    {
        m_objConfiguration = objConfiguration;
        m_objWorkingConfiguration = new QCConfiguration(m_objConfiguration.getQC(), null);
        
        m_objWorkingConfiguration.ReadDataFrom(m_objConfiguration);
        
        if (m_objFrame == null)
        {
            m_objFrame = new JFrame();
            
            try
            {
                m_objFrame.setIconImage(new ImageIcon(Op.load("QC/edit.png").getImage(null)).getImage());
            }
            catch (Exception ex) 
            {
                ex.printStackTrace();
            }
            
            initComponents();
            
            Dimension l_objScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
            m_objFrame.setLocation(l_objScreenDimension.width/2-m_objFrame.getWidth()/2, l_objScreenDimension.height/2-m_objFrame.getHeight()/2);            

            m_objFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            m_objFrame.addWindowListener(new WindowListener() 
            {
                @Override
                public void windowClosing(WindowEvent e) 
                {
                    int l_iSelectedOption = JOptionPane.showConfirmDialog(self().m_objFrame, 
                                                      "Are you sure? Any changes not saved will be lost...", 
                                                      "Did you save the changes?", 
                                                      JOptionPane.YES_NO_OPTION,
                                                      JOptionPane.QUESTION_MESSAGE); 
                    
                    if (l_iSelectedOption == JOptionPane.YES_OPTION) 
                    {
                        self().getConfiguration().getQC().UpdateQC(true, false);
                        self().m_objFrame.setVisible(false);
                    }
                }

                public void windowOpened(WindowEvent e) {}
                public void windowClosed(WindowEvent e) {}
                public void windowIconified(WindowEvent e) {}
                public void windowDeiconified(WindowEvent e) {}
                public void windowActivated(WindowEvent e) {}
                public void windowDeactivated(WindowEvent e) {}
            });
        }
        else        
        {
            m_objModelTree = new DefaultTreeModel(m_objWorkingConfiguration);
            QCTree.setModel(m_objModelTree);
            
            expandAll(QCTree);
        }
        
        m_objFrame.setTitle("Editing : " + m_objWorkingConfiguration.getDescription());
        m_objFrame.setVisible(true);                    
    }

    /**
     * @return the m_objself
     */
    public QCConfig self() {
        return m_objSelf;
    }
}
