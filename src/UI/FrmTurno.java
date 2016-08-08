
package UI;


import BL.PersonaJpaController;
import BL.TurnoJpaController;
import DL.Persona;
import DL.Turno;
import java.time.Clock;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmTurno extends javax.swing.JFrame {
    
EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaCitasMedicasPU"); 
PersonaJpaController rf1 = new PersonaJpaController(emf);
Persona persona;
TurnoJpaController rf = new TurnoJpaController(emf);
Turno turno = new Turno ();
List<Turno> listaTurnos ; 

    FrmBuscarPaciente frmBuscarPaciente;
        
String nombres, apellidos="",nturno,fecha,hora,nespecialidad;
int idturno;
   
  
    public FrmTurno(FrmBuscarPaciente frmBuscarPaciente,Persona persona) {
        this.frmBuscarPaciente = frmBuscarPaciente;
       this.persona = persona;
        initComponents();
        obtnerInfo();
        llenarTablaTurnosDisponibles();
    }
    
    //obtener datos desde el formulario buscar paciente
    public void obtnerInfo(){
        lblCedula.setText(frmBuscarPaciente.getIdentificacion());
        lblNombres.setText(frmBuscarPaciente.getNombres());
        lblApPat.setText(frmBuscarPaciente.getApellidoPat());
        lblApMat.setText(frmBuscarPaciente.getApellidoMat());
        System.out.println("Entró");
    }

    public void llenarTablaTurnosDisponibles(){
        DefaultTableModel modelo = (DefaultTableModel) tblTurnos.getModel();
        listaTurnos=rf.findTurnoEntities();
        Object [] fila=new Object[6];
      
        for (Turno listaTurno : listaTurnos) {
            if (listaTurno.getDisponible().toString().equals("0")) {
            fila[0]=listaTurno.getIdTurno();
            fila[1]=listaTurno.getIdEspecialidad().getDescripcion();
            fila[2]=listaTurno.getFechaCita();
            fila[3]=listaTurno.getHora();
           
            modelo.addRow(fila);
            tblTurnos.setModel(modelo);
             } 
        }
    }
    
        public void llenarTablaTurnosAsignados(){
        DefaultTableModel modelo = (DefaultTableModel) tblTurnos.getModel();
        listaTurnos=rf.findTurnoEntities();
        Object [] fila=new Object[6];
      
        for (Turno listaTurno : listaTurnos) {
            if (listaTurno.getDisponible().toString().equals("1")) {
            fila[0]=listaTurno.getIdTurno();
            fila[1]=listaTurno.getIdEspecialidad().getDescripcion();
            fila[2]=listaTurno.getFechaCita();
            fila[3]=listaTurno.getHora().getHours();
           
            modelo.addRow(fila);
            tblTurnos.setModel(modelo);
             } 
        }
    }
    
     public void llenarTablaTurnos(){
        DefaultTableModel modelo = (DefaultTableModel) tblTurnos.getModel();
        listaTurnos=rf.findTurnoEntities();
        Object [] fila=new Object[6];
      
        for (Turno listaTurno : listaTurnos) {
            fila[0]=listaTurno.getIdTurno();
            fila[1]=listaTurno.getIdEspecialidad().getDescripcion();
            fila[2]=listaTurno.getFechaCita().getDate();
            fila[3]=listaTurno.getHora().getTime();
           
            modelo.addRow(fila);
            tblTurnos.setModel(modelo);
        }
    }
    
    public void mensajeConfirmarTurno (){
         int fila = tblTurnos.getSelectedRow();
         nombres = lblNombres.getText();
         apellidos += lblApPat.getText() + " " + lblApMat.getText();
         nespecialidad = tblTurnos.getValueAt(fila, 1).toString();
         fecha = tblTurnos.getValueAt(fila, 2).toString();
         hora=tblTurnos.getValueAt(fila, 3).toString();
         
         JOptionPane.showMessageDialog(null, "   Confirmación de Cita Médica  \n" + 
                                        "\nNombres:               " + nombres + 
                                        "\nApellidos:               " + apellidos + 
                                        "\nEspecialidad:         "+nespecialidad+
                                        "\nFecha Cita:             "+fecha+
                                        "\nHora:                       "+hora);
    
    }
    
    public void asignarTurno() throws Exception{
        int fila = tblTurnos.getSelectedRow();
        idturno = (int)tblTurnos.getValueAt(fila, 0);
       int id =rf1.findByCedula(lblCedula.getText()).getIdpersona();
       
             
       turno = rf.findTurno(idturno).setIdpersona(id);
       // rf.edit(turno.);
        //int idpersona = persona.getIdpersona();

        //rf.edit(turno);

        // turno.setIdpersona(persona);
         //rf.edit(turno);
        
        
     //   rf.findTurno(idturno).setIdpersona(persona);
        //turno.setIdpersona(persona.getIdpersona());
        System.out.println("Se alamacenó: " +id+ " ideturno"+ idturno );
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbEspecialidad = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        lblApPat = new javax.swing.JLabel();
        lblApMat = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTurnos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Asignar Cita Médica");

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información del Paciente"));

        jLabel3.setText("Nombres: ");

        jLabel4.setText("Apellido Paterno: ");

        jLabel5.setText("Apellido Materno: ");

        jLabel1.setText("Seleccionar Especialidad");

        cmbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disponibles", "Asignados", "Todos" }));
        cmbEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEspecialidadActionPerformed(evt);
            }
        });

        jLabel2.setText("N° Cédula:");

        lblCedula.setText("jLabel6");

        lblNombres.setText("jLabel6");

        lblApPat.setText("jLabel6");

        lblApMat.setText("jLabel6");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblApMat)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(43, 43, 43)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombres)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(lblCedula)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 337, Short.MAX_VALUE)
                                        .addComponent(jLabel1)
                                        .addGap(32, 32, 32)
                                        .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblApPat)))
                        .addGap(76, 76, 76))))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblCedula))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblNombres)))
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblApPat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblApMat))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cmbEspecialidad, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblCedula, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblNombres, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblApPat, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblApMat, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Disponibilidad de Turnos"));

        tblTurnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Número de turno", "Especialidad", "Fecha de cita", "Horarios Disponibles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTurnos);

        jButton1.setText("Confirmar Turno");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(108, 108, 108))
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );
        jLayeredPane2.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1)
                    .addComponent(jLayeredPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEspecialidadActionPerformed
   
             if (cmbEspecialidad.getSelectedItem().toString().equals("Todos"))
             {
                 llenarTablaTurnos();
             }
             
              if (cmbEspecialidad.getSelectedItem().toString().equals("Disponibles"))
             {
                 llenarTablaTurnosDisponibles();
             }
              
               if (cmbEspecialidad.getSelectedItem().toString().equals("Asignados"))
             {
                 llenarTablaTurnosAsignados();
             }
    }//GEN-LAST:event_cmbEspecialidadActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          mensajeConfirmarTurno();
    try {
        asignarTurno();
    } catch (Exception ex) {
        Logger.getLogger(FrmTurno.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbEspecialidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApMat;
    private javax.swing.JLabel lblApPat;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JTable tblTurnos;
    // End of variables declaration//GEN-END:variables
}
