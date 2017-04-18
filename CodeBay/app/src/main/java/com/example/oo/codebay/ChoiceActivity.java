package com.example.oo.codebay;



        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import java.util.HashMap;

public class ChoiceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private RecyclerView rvCategory;
    HashMap<String, Integer> choicesTech = new HashMap<>();
    HashMap<String, Integer> choicesFashion = new HashMap<>();
    HashMap<String, Integer> choicesEducation = new HashMap<>();
      private int categoryType;
    private Object[] feedNames;
    private HashMap<String, Integer> megaList;
    private ListView lvChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        choicesTech.put("Techcrunch", R.string.techcrunch);
        choicesTech.put("ZDNet", R.string.zdnet);
        choicesTech.put("techRepublic", R.string.techRepublic);
        choicesTech.put("Wired", R.string.wired);
        choicesTech.put("Computer Weekly", R.string.computer);
        choicesTech.put("Cnet", R.string.cnet);
        choicesTech.put("Techradar", R.string.techradar);




        lvChoice = (ListView) findViewById(R.id.lvChoice);
        if (getIntent() != null && getIntent().hasExtra("trainedge.scoop.EXTRA_CATEGORY")) {
            String category = getIntent().getStringExtra("trainedge.scoop.EXTRA_CATEGORY");
            getSupportActionBar().setSubtitle(category);
            categoryType = getIntent().getIntExtra("trainedge.scoop.EXTRA_POS", 0);
            ArrayAdapter<Object> adapter = null;
            switch (categoryType) {
                case 0:
                    feedNames = choicesAndroid.keySet().toArray();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedNames);
                    break;
                case 1:
                    feedNames = choicesJavascript.keySet().toArray();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedNames);
                    break;
                case 2:
                    feedNames = choicesPython.keySet().toArray();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedNames);
                    break;

                case -1:
                    megaList = new HashMap<String, Integer>();
                    megaList.putAll(choicesTech);
                    megaList.putAll(choicesFashion);
                    megaList.putAll(choicesEducation);


                    feedNames = megaList.keySet().toArray();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedNames);
                    break;
            }
            lvChoice.setAdapter(adapter);
            lvChoice.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedUrl = null;
        String feedKey = feedNames[i].toString();
        switch (categoryType) {
            case 0:
                selectedUrl = getResources().getString(choicesAndroid.get(feedKey));
                break;
            case 1:
                Integer id = choicesJavascript.get(feedKey);
                selectedUrl = getResources().getString(id);

                break;
            case 2:
                selectedUrl = getResources().getString(choicesPython.get(feedKey));
                break;

            case -1:
                selectedUrl = getResources().getString(megaList.get(feedKey));
                break;
        }
        Intent feedIntent = new Intent(this, FeedActivity.class);
        feedIntent.putExtra("com.example.oo.codebay.EXTRA_URL", selectedUrl);
        feedIntent.putExtra("com.example.oo.codebay.EXTRA_NAME", feedKey);
        startActivity(feedIntent);

    }

}