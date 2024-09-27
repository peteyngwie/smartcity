package com.tra.loginscreen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.appcompat.widget.SearchView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toolbar;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

public class findcable extends AppCompatActivity {

    MyCustomAdapter dataAdapter = null;

    Context context ;

    private androidx.appcompat.widget.SearchView editsearch;   // cable search view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_findcable);
        //Generate list View from ArrayList

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.cabledatsearchtoolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),DashBoardLinearActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        displayListView();

    }  // end of oncreate

    private void displayListView() {

        Country1 country ;
        //Array list of countries

        ArrayList<Country1> countryList = new ArrayList<Country1>();


        for ( int i = 0 ; i < 100 ; i ++ ) {

            if (i % 2 == 0) {
                country = new Country1("0ce2801190200078a4", "1", "中偉科技", "11F-1");
                countryList.add(country);
            } else if (i % 3 == 0) {
                country = new Country1("0ce280119020007432", "2", "亞森特科技", "7F-2");
                countryList.add(country);
            } else if (i % 5 == 0) {
                country = new Country1("0ce2801190200075e8", "3", "台積電", "1F");
                countryList.add(country);
            } else if (i % 7 == 0) {
                country = new Country1("0ce280119020007547", "4", "智崴科技", "12F-2");
                countryList.add(country);
            } else if (i % 9 == 0) {
                country = new Country1("0ce2801190200074ea", "5", "中科院", "8F");
                countryList.add(country);
            }
            else if (i % 13 == 0) {
                country = new Country1("0ce2801190200031e7", "6", "中鋼", "5F");
                countryList.add(country);
            }
        }
        //create an Array Adaptar from the String Array

        dataAdapter = new MyCustomAdapter(this,
                R.layout.country_info3, countryList);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Object  c = parent.getItemAtPosition(position);
                Toast.makeText(findcable.this, "XXXXXXX", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        EditText myFilter = (EditText) findViewById(R.id.myFilter); // Edit text for search

        // The following codes that focuses edit text contents change

        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                dataAdapter.getFilter().filter(s.toString());  // focus on the edit text for filter

            }
        });




         editsearch = (androidx.appcompat.widget.SearchView) findViewById(R.id.cabledatsearch);


        // 设置搜索文本监听
        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(countryList.contains(query))
                    dataAdapter.getFilter().filter(query);
                else
                    Toast.makeText(context, "hello ", Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });



    }

    private class MyCustomAdapter extends ArrayAdapter<Country1> {

        private ArrayList<Country1> originalList;
        private ArrayList<Country1> countryList;
        private CountryFilter filter;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Country1> countryList) {
            super(context, textViewResourceId, countryList);

            this.countryList = new ArrayList<Country1>();
            this.countryList.addAll(countryList);
            this.originalList = new ArrayList<Country1>();
            this.originalList.addAll(countryList);
        }

        @Override
        public Filter getFilter() {

            if (filter == null){
                filter  = new CountryFilter(); // allocate a filter
            }

            return filter;
        }


        private class ViewHolder {
            TextView code;
            TextView name;
            TextView continent;
            TextView region;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info3, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.continent = (TextView) convertView.findViewById(R.id.continent);
                holder.region = (TextView) convertView.findViewById(R.id.region);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country1 country = countryList.get(position);
            holder.code.setText(country.getCode());
            holder.name.setText(country.getName());
            holder.continent.setText(country.getContinent());
            holder.region.setText(country.getRegion());

            return convertView;

        }

        private class CountryFilter extends Filter
        {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if(constraint != null && constraint.toString().length() > 0)
                {
                    ArrayList<Country1> filteredItems = new ArrayList<Country1>();

                    for(int i = 0, l = originalList.size(); i < l; i++)
                    {
                        Country1 country = originalList.get(i);
                        if(country.toString().toLowerCase().contains(constraint))
                            filteredItems.add(country);
                    }

                    result.count = filteredItems.size();
                    result.values = filteredItems;
                }
                else
                {
                    synchronized(this)
                    {
                        result.values = originalList;
                        result.count = originalList.size();
                    }
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {

                countryList = (ArrayList<Country1>)results.values;
                notifyDataSetChanged();
                clear();

                for(int i = 0, l = countryList.size(); i < l; i++)
                    add(countryList.get(i));

                notifyDataSetInvalidated();

            }
        }


    }
}