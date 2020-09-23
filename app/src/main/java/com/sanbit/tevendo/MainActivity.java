package com.sanbit.tevendo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sanbit.tevendo.Clientes.ClientesFragment;
import com.sanbit.tevendo.ListadoVentas.ListadoVentasFragment;
import com.sanbit.tevendo.Productos.Estados.ProductosEstadosFragment;
import com.sanbit.tevendo.Productos.ProductosFragment;
import com.sanbit.tevendo.ShareUtil.DataCache;
import com.sanbit.tevendo.ShareUtil.LocationGeo;
import com.sanbit.tevendo.Sincronizar.SincronizarFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Handler switchHandler = new Handler();
    private Context mContext;
    private NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    private Boolean bolBienvenida =true;
    private TextView titleMenu;
    MenuItem menucli;
    @Override
    public void onResume() {
        super.onResume();

        this.setTitle("");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationGeo.getInstance(getApplicationContext(),this);
        LocationGeo.PedirPermisoApp();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        titleMenu=(TextView)findViewById(R.id.id_title_main);
        DataCache.tvTitleMenu =titleMenu;
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.id_navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);
        //First start (Inbox Fragment)

        setFragment(2);

        setupUserBox();


        menucli.setChecked(true);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // String roles  = UserPrefs.getInstance(getApplicationContext()).getRoles();

                switch (item.getItemId()) {

                    case R.id.item_cliente:

                        item.setChecked(true);
                        setFragment(2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_navigation_drawer_productos:
                        setFragment(3);
                        item.setChecked(true);
                        //setFragment(2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_navigation_drawer_entregados:
                        setFragment(4);
                        item.setChecked(true);
                        //setFragment(2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.item_Ventas:
                        setFragment(5);
                        item.setChecked(true);
                        //setFragment(2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_sincronizar:
                        setFragment(1);
                        item.setChecked(true);
                        //setFragment(2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.item_navigation_drawer_help_and_feedback:
                        item.setChecked(true);
                        setFragment(21);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return true;
            }
        });
    }
    public void setFragment(int position){
        Fragment frag = null;
        String tag = "";
        boolean switchFrag = true;
        switch (position){
            case 0:
                loadWelcomeFragment();
                break;
            case 1:
                returnToMain();
                frag = new SincronizarFragment();
                tag = Constantes.TAG_SINCRONIZACION;
                break;
            case 2:
                returnToMain();
                frag = new ClientesFragment();
                tag = "Clientes";
                break;
            case 3:
                returnToMain();
                frag = new ProductosEstadosFragment();
                tag = Constantes.TAG_SINCRONIZACION;
                break;
            case 4:
                returnToMain();
                /*frag = new ListPedidosFragment(3);
                tag = Constantes.TAG_PEDIDOS;*/
                break;
            case 5:
                returnToMain();
                frag = new ListadoVentasFragment() ;
                tag = Constantes.TAG_SINCRONIZACION;
                break;

            case 21:

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder
                        .setTitle("Tu Entrega")
                        .setMessage("Está seguro(a) de finalizar la sesión?.")
                        .setIcon(R.drawable.ic_iinfo)
                        .setPositiveButton(mContext.getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {



                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        /*startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        finish();*/
                                    }
                                });
                            }
                        })
                        .setNegativeButton(mContext.getResources().getString(R.string.cancel), null)
                        .show();
                break;
        }
        if (switchFrag) {
            switchFragment(frag,tag);
            //addFragment(new WelcomeFragment(),frag,tag);
        }
    }
    public void switchFragment(final Fragment frag, final String tag){
        switchHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.setCustomAnimations(R.transition.left_in,R.transition.left_out);

                    ft.addToBackStack(tag)
                            .replace(R.id.fragmentMain, frag,tag)
                            .commit();
                }catch(Exception e){}
            }
        }, 100);
    }

    private void setupUserBox() {
        // TODO: Poblar más views, agregar más acciones

        // Poner email
        TextView userNameView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_name);
        TextView userEmailView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_email);
       /* String userName = UserPrefs.getInstance(mContext).getUserName();
        String userEmail = UserPrefs.getInstance(mContext).getUserEmail();*/
        /*userNameView.setText("Bienvenido : ");
        userEmailView.setText(nameRepartidor);*/
        userNameView.setText("Bienvenido : Usuario Invitado");
        userEmailView.setText("Telf: 78140596");

        menucli= navigationView.getMenu().findItem(R.id.item_cliente);
        MenuItem menuped = navigationView.getMenu().findItem(R.id.item_navigation_drawer_productos);
        MenuItem menuMapa = navigationView.getMenu().findItem(R.id.item_sincronizar);
        MenuItem menupedEntregados = navigationView.getMenu().findItem(R.id.item_navigation_drawer_entregados);

        menucli.setVisible(true);
        menuped.setVisible(true);
        menuMapa.setVisible(true);
        menupedEntregados.setVisible(true);


    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (getSupportFragmentManager().getBackStackEntryCount() > 1){
                    //getSupportFragmentManager().popBackStack("WELCOMEFRAGMENT", 0);
                    getSupportFragmentManager().popBackStack();
                } else {
                    if (getSupportFragmentManager().getFragments().size() >1){
                        removeAllFragments();
                        loadWelcomeFragment();
                        return true;
                    }
                    if (doubleBackToExitPressedOnce){
                        super.onBackPressed();
                        finish();
                        return true;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(mContext, "Click nuevamente para salir.", Toast.LENGTH_SHORT).show();
                    //Please click BACK again to exit
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 2000);
                    return true;
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.byte_code, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadWelcomeFragment(){
        /*switchHandler.post(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("WELCOMEFRAGMENT")
                        .replace(R.id.fragment, new MainFragment())
                        .commit();
            }
        });*/
    }

    public void CambiarFragment(final Fragment fragmento, final String tag){
        switchHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.addToBackStack(tag)
                            .replace(R.id.fragmentMain, fragmento,tag)
                            .commit();
                }catch(Exception e){}
            }
        }, 100);
    }
    public  void returnToMain(){

        //getSupportFragmentManager().popBackStack("WELCOMEFRAGMENT",0);
        removeAllFragments();
        loadWelcomeFragment();
    }

    public void removeAllFragments() {

        if (getSupportFragmentManager().getFragments().size() > 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (Fragment activeFragment : getSupportFragmentManager().getFragments()) {
                fragmentTransaction.remove(activeFragment);
            }
            getSupportFragmentManager().getFragments().clear();
            fragmentTransaction.commit();
            getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
