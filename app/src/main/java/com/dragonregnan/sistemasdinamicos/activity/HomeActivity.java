package com.dragonregnan.sistemasdinamicos.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonregnan.sistemasdinamicos.R;
import com.dragonregnan.sistemasdinamicos.dao.almacenesDAO;
import com.dragonregnan.sistemasdinamicos.dao.balancesDAO;
import com.dragonregnan.sistemasdinamicos.dao.empresasDAO;
import com.dragonregnan.sistemasdinamicos.dao.encadenamientosDAO;
import com.dragonregnan.sistemasdinamicos.dao.nivelesVariablesDAO;
import com.dragonregnan.sistemasdinamicos.model.balancesModel;
import com.dragonregnan.sistemasdinamicos.model.nivelesVariablesModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by laura on 07/12/2015.
 */
public class HomeActivity  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private almacenesDAO almaDAO;
    private nivelesVariablesDAO nivVariableDAO;
    private empresasDAO empDAO;
    private encadenamientosDAO encDAO;
    private balancesDAO balDAO;

    private int idalmacen1;
    private int idalmacen2;
    private int produccion;
    private int mercancias;

    private int almacen1maximo;
    private int almacen2maximo;
    private int produccionmaximo;
    private int mercanciasmaximo;

    private int almacen1Deseado;
    private int almacen2Deseado;
    private int producirDeseado;
    private int mercanciasDeseado;

    private int almacen1minimo;
    private int almacen2minimo;
    private int mercanciasminimo;

    private int almacen1actual;
    private int almacen2actual;
    private int mercanciasactual;

    private View almacen1viewdeseado;
    private View almacen1viewminimo;
    private View almacen2viewdeseado;
    private View almacen2viewminimo;
    private View mercanciasviewdeseado;
    private View mercanciasviewminimo;

    private int mayoralmacen;
    private int menoralmacen;
    private String letramercancia;
    private String letraMayoralmacen;
    private String letraMenoralmacen;


    private ArrayList<Integer> encadenamiento = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    //OBTENER EL ID DEL USUARIO LOGEADO POR MEDIO DE LAS PREFERENCIAS COMPARTIDAS

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        final int idEmpresa = pref.getInt("idEmpresa", 0);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        almaDAO = new almacenesDAO(this);
        nivVariableDAO = new nivelesVariablesDAO(this);
        encDAO= new encadenamientosDAO(this);
        empDAO = new empresasDAO(this);
        balDAO = new balancesDAO(this);

    //OBTENER LOS ALMACENES

        idalmacen1 = almaDAO.getIdAlmacen( idEmpresa,1 );
        Log.d("idalmacen1",String.valueOf(idalmacen1));
        idalmacen2 = almaDAO.getIdAlmacen( idEmpresa,2 );
        produccion = almaDAO.getIdAlmacen( idEmpresa,3 );
        mercancias = almaDAO.getIdAlmacen( idEmpresa,4 );

        Log.d("idEmpresa",String.valueOf(idEmpresa));
        final int idIndustria = empDAO.getIndustriaEmpresa(idEmpresa);
        Log.d("idIndustria",String.valueOf(idIndustria));
        if(idIndustria == 1){
            letramercancia="A";
        }
        if(idIndustria == 2){
            letramercancia="B";
        }
        if(idIndustria == 3){
            letramercancia="C";
        }
        if(idIndustria == 4){
            letramercancia="D";
        }
        if(idIndustria == 5){
            letramercancia="E";
        }
        if(idIndustria == 6){
            letramercancia="F";
        }
        ArrayList<Integer> encadenamiento =  encDAO.getIndustriasVenden(idIndustria);

        if(encadenamiento.get(0).intValue() < encadenamiento.get(1).intValue()){

            menoralmacen = encadenamiento.get(0).intValue();
            mayoralmacen = encadenamiento.get(1).intValue();
        }else {
            mayoralmacen = encadenamiento.get(0).intValue();
            menoralmacen = encadenamiento.get(1).intValue();
        }

        if(menoralmacen == 1){
            letraMenoralmacen="A";
        }
        if(menoralmacen == 2){
            letraMenoralmacen="B";
        }
        if(menoralmacen == 3){
            letraMenoralmacen="C";
        }
        if(menoralmacen == 4){
            letraMenoralmacen="D";
        }
        if(menoralmacen == 5){
            letraMenoralmacen="E";
        }
        if(menoralmacen == 6){
            letraMenoralmacen="F";
        }

        if(mayoralmacen == 1){
            letraMayoralmacen="A";
        }
        if(mayoralmacen == 2){
            letraMayoralmacen="B";
        }
        if(mayoralmacen == 3){
            letraMayoralmacen="C";
        }
        if(mayoralmacen == 4){
            letraMayoralmacen="D";
        }
        if(mayoralmacen == 5){
            letraMayoralmacen="E";
        }
        if(mayoralmacen == 6){
            letraMayoralmacen="F";
        }

        TextView tv111 = (TextView) findViewById(R.id.txtvw1_1_1);
        TextView tv121 = (TextView) findViewById(R.id.txtvw1_2_1);
        TextView tv211 = (TextView) findViewById(R.id.txtvw2_1_1);

        tv111.setText("Mercancia "+ letraMenoralmacen);
        tv121.setText("Mercancia "+ letraMayoralmacen);
        tv211.setText("Mercancia "+letramercancia);

    //OBTENER NIVELES DE LOS ALMACENES

        almacen1minimo = nivVariableDAO.getMinimoDeseado(idEmpresa, idalmacen1);
        Log.d("almacen1minimo",String.valueOf(almacen1minimo));
        almacen1Deseado = nivVariableDAO.getDeseaso(idEmpresa, idalmacen1);
        Log.d("almacen1Deseado",String.valueOf(almacen1Deseado));
        almacen1actual = nivVariableDAO.getActual(idEmpresa, idalmacen1);
        Log.d("almacen1actual",String.valueOf(almacen1actual));
        almacen2minimo = nivVariableDAO.getMinimoDeseado(idEmpresa, idalmacen2);
        Log.d("almacen2minimo",String.valueOf(almacen2minimo));
        almacen2Deseado = nivVariableDAO.getDeseaso(idEmpresa, idalmacen2);
        Log.d("almacen2Deseado",String.valueOf(almacen2Deseado));
        almacen2actual = nivVariableDAO.getActual(idEmpresa, idalmacen2);
        mercanciasminimo = nivVariableDAO.getMinimoDeseado(idEmpresa, mercancias);
        mercanciasDeseado = nivVariableDAO.getDeseaso(idEmpresa, mercancias);
        mercanciasactual = nivVariableDAO.getActual(idEmpresa, mercancias);
        producirDeseado = nivVariableDAO.getDeseaso(idEmpresa, produccion);

        almacen1maximo = almaDAO.getMaximo(idEmpresa, idalmacen1);
        Log.d("almacen1maximo",String.valueOf(almacen1maximo));
        almacen2maximo = almaDAO.getMaximo(idEmpresa, idalmacen2);
        mercanciasmaximo = almaDAO.getMaximo(idEmpresa, mercancias);
        produccionmaximo = almaDAO.getMaximo(idEmpresa, produccion);

        final LinearLayout material1 = (LinearLayout) findViewById(R.id.material1);

        final LinearLayout material2 = (LinearLayout) findViewById(R.id.material2);

        final LinearLayout producto = (LinearLayout) findViewById(R.id.producto);

        final ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);

        almacen1viewdeseado = findViewById(R.id.nvl_max_1);
        almacen1viewminimo = findViewById(R.id.nvl_min_1);

        almacen2viewdeseado = findViewById(R.id.nvl_max_2);
        almacen2viewminimo = findViewById(R.id.nvl_min_2);

        mercanciasviewdeseado = findViewById(R.id.nvl_max_3);
        mercanciasviewminimo = findViewById(R.id.nvl_min_3);

        if(almacen1Deseado>0){
            int margin = ((almacen1Deseado * 100)/almacen1maximo)-5;
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewdeseado.getLayoutParams();
            relativeParams.setMargins(0, 0 , 0, margin);
            almacen1viewdeseado.setLayoutParams(relativeParams);
        }
        if(almacen1Deseado==0){
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewdeseado.getLayoutParams();
            relativeParams.setMargins(0, 0 , 0, 95);
            almacen1viewdeseado.setLayoutParams(relativeParams);
        }

        if(almacen1minimo>0){
            int margin1 = ((almacen1minimo * 100)/almacen1maximo)-5;
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewminimo.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, margin1);
            almacen1viewminimo.setLayoutParams(relativeParams);}
        if(almacen1minimo==0){
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewminimo.getLayoutParams();
            relativeParams.setMargins(0, 0, 0,95);
            almacen1viewminimo.setLayoutParams(relativeParams);
        }

        if(almacen2Deseado>0){
            int margin = ((almacen2Deseado * 100)/almacen2maximo)-5;
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewdeseado.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, margin);
            almacen2viewdeseado.setLayoutParams(relativeParams);
        }
        if(almacen2Deseado==0){
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewdeseado.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, 95);
            almacen2viewdeseado.setLayoutParams(relativeParams);
        }

        if(almacen2minimo>0){
            int margin1 = ((almacen2minimo * 100)/almacen2maximo)-5;
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewminimo.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, margin1);
            almacen2viewminimo.setLayoutParams(relativeParams);}
        if(almacen2minimo==0){
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewminimo.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, 95);
            almacen2viewminimo.setLayoutParams(relativeParams);
        }
        if(mercanciasDeseado>0){
            int margin = ((mercanciasDeseado * 100)/mercanciasmaximo)-5;
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewdeseado.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, margin);
            mercanciasviewdeseado.setLayoutParams(relativeParams);
        }
        if(mercanciasDeseado==0){
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewdeseado.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, 95);
            mercanciasviewdeseado.setLayoutParams(relativeParams);
        }

        if(mercanciasminimo>0){
            int margin1 = ((mercanciasminimo * 100)/mercanciasmaximo)-5;
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewminimo.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, margin1);
            mercanciasviewminimo.setLayoutParams(relativeParams);}
        if(mercanciasminimo==0){
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewminimo.getLayoutParams();
            relativeParams.setMargins(0, 0, 0, 95);
            mercanciasviewminimo.setLayoutParams(relativeParams);
        }

    //MODIFICAR NIVELES DEL ALMACEN 1

        material1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Almacen");
                dialog.show();
                TextView info1 = (TextView) dialog.findViewById(R.id.txtvw_inf);
                info1.setText("La capacidad total de tu alamacen es de: " + almacen1maximo);
                TextView  info2 = (TextView) dialog.findViewById(R.id.txtvw_inf2);
                info2.setText("Cantidad almacenada actual: " + almacen1actual);
                TextView info3 = (TextView) dialog.findViewById(R.id.txtvw_estado);
                info3.setText(String.valueOf(almacen1maximo));
                SeekBar seekBar =(SeekBar) dialog.findViewById(R.id.seekBar);
                seekBar.setProgress(almacen1Deseado);
                seekBar.setMax(almacen1maximo);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progressChanged = 0;

                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressChanged = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        TextView textView = (TextView) dialog.findViewById(R.id.txtvw_estado);
                        textView.setText(String.valueOf(seekBar.getProgress()));
                        almacen1Deseado = seekBar.getProgress();
                    }
                });
                SeekBar seekBar2 =(SeekBar) dialog.findViewById(R.id.seekBar2);
                seekBar2.setProgress(almacen1minimo);
                seekBar2.setMax(almacen1maximo);
                seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progressChanged = 0;

                    public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                        progressChanged = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar2) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar2) {
                        TextView textView2 = (TextView) dialog.findViewById(R.id.txtvw_estado2);
                        textView2.setText(String.valueOf(seekBar2.getProgress()));
                        almacen1minimo = seekBar2.getProgress();
                    }
                });
                Button dialogAceptar = (Button) dialog.findViewById(R.id.btn_aceptar);
                dialogAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(almacen1Deseado>almacen1minimo){

                            if(almacen1Deseado>0){
                                int margin = ((almacen1Deseado * 100)/almacen1maximo)-5;
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewdeseado.getLayoutParams();
                                relativeParams.setMargins(0, 0 , 0, margin);
                                almacen1viewdeseado.setLayoutParams(relativeParams);
                            }
                            if(almacen1Deseado==0){
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewdeseado.getLayoutParams();
                                relativeParams.setMargins(0, 0 , 0, 95);
                                almacen1viewdeseado.setLayoutParams(relativeParams);
                            }

                            if(almacen1minimo>0){
                                int margin1 = ((almacen1minimo * 100)/almacen1maximo)-5;
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewminimo.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, margin1);
                                almacen1viewminimo.setLayoutParams(relativeParams);}
                            if(almacen1minimo==0){
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen1viewminimo.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0,95);
                                almacen1viewminimo.setLayoutParams(relativeParams);
                            }
                            nivelesVariablesModel nivModel = new nivelesVariablesModel();
                            nivModel.setIdAlmacen(idalmacen1);
                            nivModel.setIdEmpresa(idEmpresa);
                            nivModel.setActual(almacen1actual);
                            nivModel.setDeseado(almacen1Deseado);
                            nivModel.setMinimoDeseado(almacen1minimo);

                            nivVariableDAO.insertNivel(nivModel);

                            dialog.dismiss();
                        }
                        else{
                            Context context = getApplicationContext();
                            CharSequence text = "El nivel mínimo no puede ser mayor al nivel maximo";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });
                Button dialogCancelar = (Button) dialog.findViewById(R.id.btn_cancelar);
                dialogCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    //MODIFICAR NIVELES ALMACEN 2

        material2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Almacen");
                dialog.show();
                TextView info1 = (TextView) dialog.findViewById(R.id.txtvw_inf);
                info1.setText("La capacidad total de tu almacen es de: " + almacen2maximo);
                TextView  info2 = (TextView) dialog.findViewById(R.id.txtvw_inf2);
                info2.setText("Cantidad almacenada actual: " + almacen2actual);
                TextView info3 = (TextView) dialog.findViewById(R.id.txtvw_estado);
                info3.setText(String.valueOf(almacen2maximo));
                SeekBar seekBar =(SeekBar) dialog.findViewById(R.id.seekBar);
                seekBar.setProgress(almacen2Deseado);
                seekBar.setMax(almacen2maximo);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progressChanged = 0;

                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressChanged = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        TextView textView = (TextView) dialog.findViewById(R.id.txtvw_estado);
                        textView.setText(String.valueOf(seekBar.getProgress()));
                        almacen2Deseado = seekBar.getProgress();
                    }
                });
                SeekBar seekBar2 =(SeekBar) dialog.findViewById(R.id.seekBar2);
                seekBar2.setProgress(almacen2minimo);
                seekBar2.setMax(almacen2maximo);
                seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progressChanged = 0;

                    public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                        progressChanged = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar2) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar2) {
                        TextView textView2 = (TextView) dialog.findViewById(R.id.txtvw_estado2);
                        textView2.setText(String.valueOf(seekBar2.getProgress()));
                        almacen2minimo = seekBar2.getProgress();
                    }
                });
                Button dialogAceptar = (Button) dialog.findViewById(R.id.btn_aceptar);
                dialogAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(almacen2Deseado>almacen2minimo){

                            if(almacen2Deseado>0){
                                int margin = ((almacen2Deseado * 100)/almacen2maximo)-5;
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewdeseado.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, margin);
                                almacen2viewdeseado.setLayoutParams(relativeParams);
                            }
                            if(almacen2Deseado==0){
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewdeseado.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, 95);
                                almacen2viewdeseado.setLayoutParams(relativeParams);
                            }

                            if(almacen2minimo>0){
                                int margin1 = ((almacen2minimo * 100)/almacen2maximo)-5;
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewminimo.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, margin1);
                                almacen2viewminimo.setLayoutParams(relativeParams);}
                            if(almacen2minimo==0){
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) almacen2viewminimo.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, 95);
                                almacen2viewminimo.setLayoutParams(relativeParams);
                            }
                            nivelesVariablesModel nivModel = new nivelesVariablesModel();
                            nivModel.setIdAlmacen(idalmacen2);
                            nivModel.setIdEmpresa(idEmpresa);
                            nivModel.setActual(almacen2actual);
                            nivModel.setDeseado(almacen2Deseado);
                            nivModel.setMinimoDeseado(almacen2minimo);

                            nivVariableDAO.insertNivel(nivModel);
                            dialog.dismiss();
                        }
                        else{
                            Context context = getApplicationContext();
                            CharSequence text = "El nivel mínimo no puede ser mayor al nivel maximo";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });
                Button dialogCancelar = (Button) dialog.findViewById(R.id.btn_cancelar);
                dialogCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    // MODIFUCAR LA PRODUCCION

        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Almacen");
                dialog.show();
                TextView info1 = (TextView) dialog.findViewById(R.id.txtvw_inf);
                info1.setText("La capacidad total de tu alamacen es de: " + mercanciasmaximo);
                TextView  info2 = (TextView) dialog.findViewById(R.id.txtvw_inf2);
                info2.setText("Cantidad almacenada actual: " + mercanciasactual);
                TextView info3 = (TextView) dialog.findViewById(R.id.txtvw_estado);
                info3.setText(String.valueOf(mercanciasmaximo));
                SeekBar seekBar =(SeekBar) dialog.findViewById(R.id.seekBar);
                seekBar.setMax(mercanciasmaximo);
                seekBar.setProgress(mercanciasDeseado);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progressChanged = 0;

                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressChanged = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        TextView textView = (TextView) dialog.findViewById(R.id.txtvw_estado);
                        textView.setText(String.valueOf(seekBar.getProgress()));
                        mercanciasDeseado = seekBar.getProgress();
                    }
                });
                SeekBar seekBar2 =(SeekBar) dialog.findViewById(R.id.seekBar2);
                seekBar2.setMax(mercanciasmaximo);
                seekBar2.setProgress(mercanciasminimo);
                seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progressChanged = 0;

                    public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                        progressChanged = progress;
                    }

                    public void onStartTrackingTouch(SeekBar seekBar2) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar2) {
                        TextView textView2 = (TextView) dialog.findViewById(R.id.txtvw_estado2);
                        textView2.setText(String.valueOf(seekBar2.getProgress()));
                        mercanciasminimo = seekBar2.getProgress();
                    }
                });
                Button dialogAceptar = (Button) dialog.findViewById(R.id.btn_aceptar);
                dialogAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mercanciasDeseado>mercanciasminimo){

                            if(mercanciasDeseado>0){
                                int margin = ((mercanciasDeseado * 100)/mercanciasmaximo)-5;
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewdeseado.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, margin);
                                mercanciasviewdeseado.setLayoutParams(relativeParams);
                            }
                            if(mercanciasDeseado==0){
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewdeseado.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, 95);
                                mercanciasviewdeseado.setLayoutParams(relativeParams);
                            }

                            if(mercanciasminimo>0){
                                int margin1 = ((mercanciasminimo * 100)/mercanciasmaximo)-5;
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewminimo.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, margin1);
                                mercanciasviewminimo.setLayoutParams(relativeParams);}
                            if(mercanciasminimo==0){
                                RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) mercanciasviewminimo.getLayoutParams();
                                relativeParams.setMargins(0, 0, 0, 95);
                                mercanciasviewminimo.setLayoutParams(relativeParams);
                            }
                            nivelesVariablesModel nivModel = new nivelesVariablesModel();
                            nivModel.setIdAlmacen(mercancias);
                            nivModel.setIdEmpresa(idEmpresa);
                            nivModel.setActual(mercanciasactual);
                            nivModel.setDeseado(mercanciasDeseado);
                            nivModel.setMinimoDeseado(mercanciasminimo);

                            nivVariableDAO.insertNivel(nivModel);

                            dialog.dismiss();
                        }
                        else{
                            Context context = getApplicationContext();
                            CharSequence text = "El nivel mínimo no puede ser mayor al nivel maximo";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });
                Button dialogCancelar = (Button) dialog.findViewById(R.id.btn_cancelar);
                dialogCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_production);
                dialog.setTitle("Producción");
                dialog.show();
                TextView explica = (TextView) dialog.findViewById(R.id.txtvw_explica);
                String exp = "Tu capacidad de produccion es de "+ produccionmaximo+" piezas por dia";
                explica.setText(exp);
                Button dialogAceptar = (Button) dialog.findViewById(R.id.btn_aceptar1);
                dialogAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText producto = (EditText) dialog.findViewById(R.id.edt_producto);
                        int produciraux = Integer.parseInt(producto.getText().toString());
                        int coeficienteMayor = encDAO.getCoeficiente(idIndustria, mayoralmacen);
                        int mayorNecesario = coeficienteMayor * produciraux;
                        int coeficienteMenor = encDAO.getCoeficiente(idIndustria, menoralmacen);
                        int menorNecesario = coeficienteMenor * produciraux;
                        if (produciraux < produccionmaximo) {
                            if (menorNecesario <= almacen1actual && mayorNecesario <= almacen2actual) {
                                producirDeseado = produciraux;

                                nivelesVariablesModel nivModel = new nivelesVariablesModel();
                                nivModel.setIdAlmacen(produccion);
                                nivModel.setIdEmpresa(idEmpresa);
                                nivModel.setActual(nivVariableDAO.getActual(idEmpresa, produccion));
                                nivModel.setDeseado(producirDeseado);
                                nivModel.setMinimoDeseado(0);

                                balancesModel alma1 = new balancesModel();
                                balancesModel alma2 = new balancesModel();
                                balancesModel merca = new balancesModel();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar cal = Calendar.getInstance();

                                alma1.setIdEmpresa(idEmpresa);
                                alma1.setIdCuenta(1);
                                alma1.setFecBalance(String.valueOf(cal.getTime()));

                                float saldoactualalma1 = balDAO.getSaldo(idEmpresa, 1);
                                float precio = saldoactualalma1 / almacen1actual;
                                float saldototal = menorNecesario * precio;
                                alma1.setSaldo(saldototal);

                                balDAO.insertCuentaBalance(alma1);

                                alma2.setIdEmpresa(idEmpresa);
                                alma2.setIdCuenta(2);
                                alma2.setFecBalance(String.valueOf(cal.getTime()));
                                float saldoactualalma2 = balDAO.getSaldo(idEmpresa, 2);
                                float precio2 = saldoactualalma2 / almacen2actual;
                                float saldototal2 = mayorNecesario * precio2;
                                alma2.setSaldo(saldototal2);

                                balDAO.insertCuentaBalance(alma2);

                                merca.setIdEmpresa(idEmpresa);
                                merca.setIdCuenta(4);
                                merca.setFecBalance(String.valueOf(cal.getTime()));
                                float saldoactualmerca = balDAO.getSaldo(idEmpresa, 4);
                                float preciomerca = saldoactualmerca / mercanciasactual;
                                float aumentomercancia = producirDeseado * preciomerca;
                                float saldototalmerca = saldoactualmerca + aumentomercancia;
                                merca.setSaldo(saldototal2);

                                balDAO.insertCuentaBalance(merca);

                                nivelesVariablesModel nivalma1 = new nivelesVariablesModel();
                                nivalma1.setIdAlmacen(almaDAO.getIdAlmacen(idEmpresa, 1));
                                nivalma1.setIdEmpresa(idEmpresa);
                                nivalma1.setActual(almacen1actual - menorNecesario);
                                nivalma1.setDeseado(almacen1Deseado);
                                nivalma1.setMinimoDeseado(almacen1minimo);

                                nivelesVariablesModel nivalma2 = new nivelesVariablesModel();
                                nivalma2.setIdAlmacen(almaDAO.getIdAlmacen(idEmpresa, 2));
                                nivalma2.setIdEmpresa(idEmpresa);
                                nivalma2.setActual(almacen2actual - mayorNecesario);
                                nivalma2.setDeseado(almacen2Deseado);
                                nivalma2.setMinimoDeseado(almacen2minimo);

                                nivelesVariablesModel nivmerca = new nivelesVariablesModel();
                                nivmerca.setIdAlmacen(almaDAO.getIdAlmacen(idEmpresa, 4));
                                nivmerca.setIdEmpresa(idEmpresa);
                                nivmerca.setActual(mercanciasactual + produciraux);
                                nivmerca.setDeseado(mercanciasDeseado);
                                nivmerca.setMinimoDeseado(mercanciasminimo);

                                nivVariableDAO.insertNivel(nivModel);
                                nivVariableDAO.insertNivel(nivalma2);
                                nivVariableDAO.insertNivel(nivalma2);
                                nivVariableDAO.insertNivel(nivmerca);
                                dialog.dismiss();
                            } else {
                                Context context = getApplicationContext();
                                CharSequence text = "No hay material suficiente en los almacenes";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }

                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "La producion no puede ser mayor a tu capacidad maxima";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                    }
                });
                Button dialogCancelar = (Button) dialog.findViewById(R.id.btn_cancelar1);
                dialogCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
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



        } else if (id == R.id.nav_mercado) {
            Intent i = new Intent(this,MercadoActivity.class);
            startActivity(i);
            finish();


        } else if (id == R.id.nav_balance) {
            Intent i = new Intent(this,BalanceActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

