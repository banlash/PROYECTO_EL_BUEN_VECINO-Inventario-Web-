package com.elbuenvecino.servlet;

import com.elbuenvecino.config.ConexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        try (Connection con = ConexionDB.obtenerConexion()) {
            String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);

            ps.executeUpdate();
            System.out.println("Producto guardado con éxito.");

            response.sendRedirect("login.html");

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al guardar el producto: " + e.getMessage());
        }
    }
}