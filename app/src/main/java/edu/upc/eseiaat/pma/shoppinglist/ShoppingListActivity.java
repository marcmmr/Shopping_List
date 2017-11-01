package edu.upc.eseiaat.pma.shoppinglist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    private ArrayList<String> itemList; //creem una llista
    private ArrayAdapter<String> adapter;//creem adaptador

    private ListView list;
    private Button btn_add;
    private EditText edit_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        list = (ListView) findViewById(R.id.list);// quan escrius pots clicar enter perq t'importi la classe directament
        btn_add = (Button) findViewById(R.id.btn_add);//creem els camps enllocs de ferho amb variab locals
        edit_item = (EditText) findViewById(R.id.edit_item);

        itemList= new ArrayList<>();
        itemList.add("Coca-cola");//afegim elements
        itemList.add("Pizza 4 Formatges");
        itemList.add("Arròs");
        itemList.add("Macarrons");

        adapter = new ArrayAdapter <> ( //creem adaptador
                this,//punter a l'activitat
                android.R.layout.simple_list_item_1,
                itemList //dades
        );
        //Ara afegirem el codi per posar nous elements a la llista
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();//creem metode a shopping listactivity NO al oncreate
            }
        });

        edit_item.setOnEditorActionListener(new TextView.OnEditorActionListener() { // per afegir elements des del teclat amb el tick
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                addItem();
                return true;
            }
        });


        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {// esciure despres del new CTRL+barra
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemoveItem(pos); //creem metode en shopping list activity i NO en oncreate
                return true;
            }
        });

    }

    private void maybeRemoveItem(final int pos) { //passem com a parametre la posicio
        AlertDialog.Builder builder= new AlertDialog.Builder(this); //creem el constructor
        builder.setTitle(R.string.confirm);// titol del quadre de dialeg
        String fmt = getResources().getString(R.string.confirm_message);//creem el recurs de la pregunta
        builder.setMessage(String.format(fmt, itemList.get(pos) ));
        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemList.remove(pos);// fer final perq es una classe a part
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);// no borra res
        builder.create().show();//mostra el quadre de dialeg


    }

    private void addItem() {
        String item_text =edit_item.getText().toString(); //paseem l'editable a string
        if(!item_text.isEmpty()) { //si no esta buit ho fica a la llista
            itemList.add(item_text);
            adapter.notifyDataSetChanged(); //avisem a l'adaotador
            edit_item.setText("");//borrem el text ja que ja l'hem ficat
        }
    }
}
