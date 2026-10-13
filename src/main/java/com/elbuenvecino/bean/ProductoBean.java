package com.elbuenvecino.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.elbuenvecino.config.ConexionDB;
import com.elbuenvecino.model.Producto;

@Named
@RequestScoped
public class ProductoBean {
    private String nombre;
    private double precio;
    private int stock;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String guardar() {
        try {
            Connection con = ConexionDB.obtenerConexion();
            String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.nombre);
            ps.setDouble(2, this.precio);
            ps.setInt(3, this.stock);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "productos.xhtml?faces-redirect=true";
    }

    public List<Producto> getListaProductos() {
        List<Producto> lista = new ArrayList<>();
        try {
            Connection con = ConexionDB.obtenerConexion();
            String sql = "SELECT * FROM productos";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Producto(rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock")));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}