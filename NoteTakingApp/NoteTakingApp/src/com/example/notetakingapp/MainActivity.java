package com.example.notetakingapp;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notetakingapp.data.NoteItem;
import com.example.notetakingapp.data.NotesDataSource;

public class MainActivity extends ListActivity {

	private NotesDataSource datasource;
	List<NoteItem>notesList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datasource = new NotesDataSource(MainActivity.this);
        refreshDisplay();
        
        
           
    }


    private void refreshDisplay() {
		notesList = datasource.findAll();
		ArrayAdapter<NoteItem>adapter = new ArrayAdapter<NoteItem>(MainActivity.this, R.layout.list_item_layout,notesList); 
		setListAdapter(adapter);
    	
		
	}


		public boolean onOptionsMenu(Menu menu){
			getMenuInflater().inflate(R.menu.main,menu);
			return true;
			
		}
	





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
	getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
}
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			if(item.getItemId() == R.id.action_create_add){
				createNote();
			}
			
			return super.onOptionsItemSelected(item);
		
		
		}
	private void createNote(){
		
		NoteItem note=NoteItem.getNew();
		
		Intent intent= new Intent (MainActivity.this,
		EditNoteActivity.class);
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText());
		startActivityForResult(intent,1001);
			
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		NoteItem note=notesList.get(position);
		Intent intent= new Intent (MainActivity.this,
				EditNoteActivity.class);
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText());
		startActivityForResult(intent,1001);
		
		
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		if (requestCode==1001 && requestCode==RESULT_OK) {
			NoteItem note=NoteItem.getNew();
			note.setKey(data.getStringExtra("key"));
			note.setText(data.getStringExtra("text"));
			datasource.update(note);
			refreshDisplay();
			
		}
	}

}






