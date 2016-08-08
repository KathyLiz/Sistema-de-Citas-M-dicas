
package UI;

import BL.PersonaJpaController;
import DL.Persona;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class FrmBuscarPaciente extends javax.swing.JFrame {

EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaCitasMedicasPU"); 
PersonaJpaController rf = new PersonaJpaController(emf);
Persona persona = new Persona ();
 String identificacion, nombres, apellidoPat, apellidoMat;


    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

        //tr.setEspecialidad("Nada ");
        //tr.setNTurno(100);
        //System.out.println("La especialidad es: "+ rf.findTurno(1).getEspecialidad());
//Getters and Setters Persona
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }        //rf.create(tr);
    
    
    public FrmBuscarPaciente() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jComboBuscar = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatosPaciente = new javax.swing.JTable();
        btnAplicar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setTitle("Buscar Paciente");
        jInternalFrame1.setVisible(true);

        jLabel1.setText("Buscar Por:");

        jComboBuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Identificación", "Apellido Paterno" }));
        jComboBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBuscarActionPerformed(evt);
            }
        });

        jLabel2.setText("Paciente");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Regresar a Asignar cita");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        tblDatosPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Identificación", "Nombres", "Apellido Paterno", "Apellido Materno", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDatosPaciente);

        btnAplicar.setText("Aplicar");
        btnAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(84, 84, 84)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(260, 260, 260))
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(jButton1))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(btnAplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(btnCancelar)))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAplicar))
                .addContainerGap(174, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if(jComboBuscar.getSelectedItem().toString().equalsIgnoreCase("Identificación")){
        buscarPorCedula();
        }
       
       if(jComboBuscar.getSelectedItem().toString().equalsIgnoreCase("Apellido Paterno")){
        buscarPorApellido();
        }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    //Obtener datos de Jtable
    public void ObtenerDatoSeleccionado(){
        int fila = tblDatosPaciente.getSelectedRow();
        identificacion =tblDatosPaciente.getValueAt(fila, 0).toString();
        nombres=tblDatosPaciente.getValueAt(fila, 1).toString();
        apellidoPat=tblDatosPaciente.getValueAt(fila, 2).toString();
        apellidoMat=tblDatosPaciente.getValueAt(fila, 3).toString();
        JOptionPane.showMessageDialog(null, "   Información del Paciente \n" + 
                                       "\nN° de cédula         "+identificacion + 
                                       "\nNombres:               " + nombres + 
                                        "\nApellido Paterno:   " + apellidoPat + 
                                        "\nApellido Materno:   "+apellidoMat);
        
    }
  
    public void buscarPorCedula(){
         DefaultTableModel modelo = (DefaultTableModel) tblDatosPaciente.getModel();
        Object [] fila=new Object[6];
        String id = (jTextField1.getText());
        
        fila[0]=rf.findByCedula(id).getCedula();
        fila[1]=rf.findByCedula(id).getNombres();
        fila[2]=rf.findByCedula(id).getPrimerapellido();
        fila[3]=rf.findByCedula(id).getSegundoapellido();
        // fila[3]=rf.findPersonaEntities();
        modelo.addRow(fila);
        tblDatosPaciente.setModel(modelo);
    }
    
    public void buscarPorApellido(){
        DefaultTableModel modelo = (DefaultTableModel) tblDatosPaciente.getModel();
        Object [] fila=new Object[6];
        String id = (jTextField1.getText());
        
        fila[0]=rf.findByApellido(id).getCedula();
        fila[1]=rf.findByApellido(id).getNombres();
        fila[2]=rf.findByApellido(id).getPrimerapellido();
        fila[3]=rf.findByApellido(id).getSegundoapellido();
        // fila[3]=rf.findPersonaEntities();
        modelo.addRow(fila);
        tblDatosPaciente.setModel(modelo);
    }
    private void jComboBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBuscarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       FrmAsignarCitas regresar = new FrmAsignarCitas();
       regresar.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarActionPerformed
        // TODO add your handling code here:
         ObtenerDatoSeleccionado();
        FrmTurno confirmar = new FrmTurno(this,this.persona);
        confirmar.setVisible(true);
        this.setVisible(false);
      
  
    }//GEN-LAST:event_btnAplicarActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAplicar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBuscar;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblDatosPaciente;
    // End of variables declaration//GEN-END:variables
}
