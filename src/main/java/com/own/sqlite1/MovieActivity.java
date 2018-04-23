package com.own.sqlite1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.own.sqlite1.adapter.MovieAdapter;
import com.own.sqlite1.model.Movie;
import com.own.sqlite1.sqlite.DBOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alo_m on 11/04/2018.
 */

public class MovieActivity  extends  AppCompatActivity{
    private EditText edtName;
    private EditText edtDirector;
    private EditText edtClasification;
    private EditText edtYear;
    private EditText edtDuration;
    private Button btnSaveMovie;
    private DBOperations operations;
    private Movie movie= new Movie();
    private RecyclerView rvMovies;
    private List<Movie> movies=new ArrayList<Movie>();
    private MovieAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //iniciacion de la isntancia
        operations=DBOperations.getDBOperations(getApplicationContext());
        movie.setIdMovie("");


        initComponents();
    }
    private void initComponents(){
        rvMovies=(RecyclerView)findViewById(R.id.rv_movie_list);
        rvMovies.setHasFixedSize(true);
        LinearLayoutManager layoutManeger=new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManeger);
        //
        getMovies();
        adapter=new MovieAdapter(movies);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_delete_movie:
                        operations.deleteMovie(movies.get(rvMovies.getChildPosition((View)v.getParent().getParent())).getIdMovie());
                        getMovies();
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_edit_movie:

                        Toast.makeText(getApplicationContext(),"Editar",Toast.LENGTH_SHORT).show();
                        Cursor c = operations.getMovieById(movies.get(
                                rvMovies.getChildPosition(
                                        (View)v.getParent().getParent())).getIdMovie());
                        if (c!=null){
                            if (c.moveToFirst()){
                                movie = new Movie(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getInt(6));
                            }
                            edtName.setText(movie.getName());
                            edtDirector.setText(movie.getDirector());
                            edtClasification.setText(movie.getClasification());
                            edtYear.setText(String.valueOf(movie.getYear()));
                            edtDuration.setText(String.valueOf(movie.getDuration()));
                        }else{
                            Toast.makeText(getApplicationContext(),"Registro no encontrado",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        });
        rvMovies.setAdapter(adapter);

        edtName=(EditText)findViewById(R.id.name_movie);
        edtDirector=(EditText)findViewById(R.id.director_movie);
        edtClasification=(EditText)findViewById(R.id.clasification_movie);
        edtYear=(EditText)findViewById(R.id.year_movie);
        edtDuration=(EditText)findViewById(R.id.duration_movie);

        btnSaveMovie=(Button)findViewById(R.id.bt_save_movie);

        btnSaveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!movie.getIdMovie().equals("")){
                    movie.setName(edtName.getText().toString());
                    movie.setDirector(edtDirector.getText().toString());
                    movie.setClasification(edtClasification.getText().toString());
                    movie.setYear(Integer.parseInt(edtYear.getText().toString()));
                    movie.setDuration(Integer.parseInt(edtDuration.getText().toString()));
                    operations.updateMovie(movie);

                }else {
                    movie = new Movie("", edtName.getText().toString(), edtDirector.getText().toString(), edtClasification.getText().toString(), Integer.parseInt(edtYear.getText().toString()), Integer.parseInt(edtDuration.getText().toString()));
                    operations.insertMovie(movie);
                }
                getMovies();
                clearData();
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void getMovies(){
        Cursor c =operations.getMovies();
        movies.clear();
        if(c!=null){
            while (c.moveToNext()){
                movies.add(new Movie(c.getString(1),c.getString(2),c.getString(3),c.getString(4), c.getInt(5), c.getInt(6)));
            }
        }

    }

    private void clearData(){
        edtName.setText("");
        edtDirector.setText("");
        edtClasification.setText("");
        edtYear.setText("");
        edtDuration.setText("");
        movie=null;
        movie= new Movie();
        movie.setIdMovie("");
    }
}

