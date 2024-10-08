package com.inventario.gui;


import javax.swing.*; // Proporciona las clases para crear la interfaz gráfica (JFrame, JTextField, JButton, JTable, etc.).
import javax.swing.table.DefaultTableModel; // Permite manejar la tabla de productos.
import java.awt.*; // Ofrece herramientas para manejar el diseño gráfico, como GridLayout y BorderLayout.
import java.awt.event.ActionEvent; // Se usa para manejar eventos como hacer clic en botones (eventos de acción).
import java.awt.event.ActionListener;
import com.inventario.model.Product; // Representa un producto en el modelo de datos (es una clase externa que aún no vemos).


public class ProductRegistrationFrame extends JFrame { // heriencia
    private JTextField nameField; // Se definen tres JTextField para ingresar el nombre, el precio y la cantidad del producto.
    private JTextField priceField;
    private JTextField quantityField;
    private JButton saveButton; //Guarda datos del producto
    private JTable productTable; //Muestra los productos registrados
    private DefaultTableModel tableModel; //Administra los datos de la tabla

    public ProductRegistrationFrame() { // constructor
        setTitle("Registro de Productos");
        setSize(600, 400); //Define el tamaño de la ventana en pixeles
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // cierra el programa
        setLocationRelativeTo(null); //Centra la ventana en la pantalla
        initComponents(); //Inicializa y organiza todos los componentes gráficos.
    }

    private void initComponents() {
        // Crear panel
        JPanel panel = new JPanel(); //Se crea un JPanel para organizar los componentes de entrada.
        panel.setLayout(new GridLayout(8, 3)); //Distribuye los componentes en una cuadrícula de 8 filas y 3 columnas.

        // Crear campos de texto
        JLabel nameLabel = new JLabel("Nombre del Producto:");
        nameField = new JTextField();

        JLabel priceLabel = new JLabel("Precio:");
        priceField = new JTextField();

        JLabel quantityLabel = new JLabel("Cantidad:");
        quantityField = new JTextField();
 // Se crean tres etiquetas (JLabel) para describir cada campo de entrada y se crean tres JTextField donde el usuario ingresará el nombre, el precio y la cantidad del producto.
        // Crear botón de guardar
        saveButton = new JButton("Guardar");
        saveButton.addActionListener(new SaveProductAction());
        // Se crea el botón Guardar y se le asigna un ActionListener llamado SaveProductAction, que manejará el evento cuando el usuario haga clic en el botón.

        // Añadir componentes al panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(new JLabel()); // Espacio vacío
        // Se agrega una etiqueta vacía para crear espacio en la cuadrícula.
        panel.add(saveButton);
        //Se agrega una etiqueta vacía para crear espacio en la cuadrícula.

        // Crear la tabla
        String[] columnNames = {"ID", "Nombre", "Precio", "Cantidad"}; //Se define un arreglo con los nombres de las columnas: ID, Nombre, Precio y Cantidad.
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable); //El JScrollPane se añade para que la tabla sea desplazable si hay muchos productos.

        // Añadir panel y tabla a la ventana
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        //Se añade el panel de entrada de productos en la parte superior (BorderLayout.NORTH) y
        // la tabla en el centro (BorderLayout.CENTER).
    }

    private class SaveProductAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                if (name.isEmpty()) {
                    throw new IllegalArgumentException("El nombre no puede estar vacío.");
                }

                // Generar un ID simple
                int id = tableModel.getRowCount() + 1;
                Product product = new Product(id, name, price, quantity);

                // Agregar producto a la tabla
                tableModel.addRow(new Object[]{id, name, price, quantity});

                // Limpiar campos
                nameField.setText("");
                priceField.setText("");
                quantityField.setText("");

                JOptionPane.showMessageDialog(ProductRegistrationFrame.this, "Producto guardado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ProductRegistrationFrame.this, "Por favor, ingresa un precio o una cantidad válida.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ProductRegistrationFrame.this, ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // para asegurar que la creación y manipulación de la interfaz gráfica ocurra en el hilo de eventos de Swing
            new ProductRegistrationFrame().setVisible(true); // Inicia la ventana (ProductRegistrationFrame) y la hace visible.
        });
    }
}