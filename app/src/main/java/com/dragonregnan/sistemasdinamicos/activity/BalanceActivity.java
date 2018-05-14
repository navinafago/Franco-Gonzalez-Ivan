package com.dragonregnan.sistemasdinamicos.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.balancesDAO;

/**
 * Created by laura on 01/03/2016.
 */
public class BalanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private balancesDAO balDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balanceactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        balDAO = new balancesDAO(this);


        TextView bancos = (TextView) findViewById(R.id.txtvwBancos);
        TextView almacen1 = (TextView) findViewById(R.id.txtvwAlmacen1);
        TextView almacen2 = (TextView) findViewById(R.id.txtvwAlmacen2);
        TextView produccion = (TextView) findViewById(R.id.txtvwProduccion);
        TextView mercado = (TextView) findViewById(R.id.txtvwMercado);
        TextView clientes = (TextView) findViewById(R.id.txtvwClientes);
        TextView totalActivos = (TextView) findViewById(R.id.txtvwTotalActivo);
        TextView proveedor1 = (TextView) findViewById(R.id.txtvwProveedor1);
        TextView proveedor2 = (TextView) findViewById(R.id.txtvwProveedor2);
        TextView capital = (TextView) findViewById(R.id.txtvwCapital);
        TextView utilidad = (TextView) findViewById(R.id.txtvwUtilidad);
        TextView totalPasivos = (TextView) findViewById(R.id.txtvwTotalPasivo);

    //OBTENER EL ID DEL USUARIO LOGEADO POR MEDIO DE LAS PREFERENCIAS COMPARTIDAS

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
         int idEmpresa = pref.getInt("idEmpresa", 0);

    //OBTENER SALDOS DE TODOS LAS CUENTAS PARA EL BALANCE

        float bancossaldo = balDAO.getSaldo(idEmpresa, 5);
        float almacen1saldo = balDAO.getSaldo(idEmpresa,1);
        float almacen2saldo = balDAO.getSaldo(idEmpresa,2);
        float produccionsaldo = balDAO.getSaldo(idEmpresa,3);
        float mercadosaldo = balDAO.getSaldo(idEmpresa,4);
        float clientessaldo = balDAO.getSaldo(idEmpresa,7);
        float proveedor1saldo = balDAO.getSaldo(idEmpresa,8);
        float proveedor2saldo = balDAO.getSaldo(idEmpresa,9);
        float capitalsaldo = balDAO.getSaldo(idEmpresa,6);

    //MOSTRAR SALDOS EN EL LAYOUT
        float activosaldo = bancossaldo + almacen1saldo + almacen2saldo + produccionsaldo + mercadosaldo + clientessaldo;
        float pasivoparcial = proveedor1saldo + proveedor2saldo + capitalsaldo;
        float utilidadsaldo = activosaldo - pasivoparcial;
        float pasivosaldo = pasivoparcial + utilidadsaldo;
        bancos.setText("Banco: $"+bancossaldo);
        almacen1.setText("Almacen1: $"+almacen1saldo);
        almacen2.setText("Almacen2: $"+almacen2saldo);
        produccion.setText("Produccion: $"+produccionsaldo);
        mercado.setText("Mercado: $"+mercadosaldo);
        clientes.setText("Clientes: $"+clientessaldo);
        totalActivos.setText("Total Activos: $"+activosaldo);
        proveedor1.setText("Proveedor1: $"+proveedor1saldo);
        proveedor2.setText("Proveedor2: $"+proveedor2saldo);
        capital.setText("Capital Social: $"+capitalsaldo);
        utilidad.setText("Utilidad: $"+utilidadsaldo);
        totalPasivos.setText("Total Pasivos: $"+pasivosaldo);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_mercado) {
            Intent i = new Intent(this,MercadoActivity.class);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_balance) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
