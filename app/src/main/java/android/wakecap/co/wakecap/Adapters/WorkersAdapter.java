package android.wakecap.co.wakecap.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wakecap.co.wakecap.Models.Attributes;
import android.wakecap.co.wakecap.Models.Item;
import android.wakecap.co.wakecap.Models.WorkersListResponse;
import android.wakecap.co.wakecap.R;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.WorkerViewHolder>  implements Filterable {

    private List<Item> workersList;
    private List<Item> workersListFiltered;

    public WorkersAdapter(List<Item> workersList) {
        this.workersList = workersList;
        this.workersListFiltered = workersList;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wokers_viewholder,parent,false);
        return  new WorkerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int i) {

            final Attributes attributes = workersList.get(i).getAttributes();
            holder.workerName.setText(attributes.getFullName());
            holder.workerRole.setText(attributes.getRoles());
            if (attributes.getInventories().size() > 0 ){
                if (attributes.getInventories().get(0).getAttributes().getIsOnline()){
                    holder.workerOnline.setImageResource(R.drawable.user_online);
                }else{
                    holder.workerOnline.setImageResource(R.drawable.user_offline);
                }
            }else{
                holder.workerOnline.setImageResource(R.drawable.user_offline);
            }




    }

    @Override
    public int getItemCount() {
//        Log.d("count", "getItemCount: " + workersList.size());
        return workersListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()){
                    workersListFiltered = workersList;
                }else{
                    List<Item> filteredResult = new ArrayList<>();

                    for (Item row : workersList){
                        if (row.getAttributes().getFullName().toLowerCase().contains(charString.toLowerCase())){
                            filteredResult.add(row);
                        }
                    }

                    workersListFiltered = filteredResult;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = workersListFiltered;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                workersListFiltered = (List<Item>)  results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class WorkerViewHolder extends RecyclerView.ViewHolder {
        public TextView workerRole;
        public TextView workerName;
        public ImageView workerOnline;


        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);
            workerOnline = itemView.findViewById(R.id.workerOnline);
            workerRole = itemView.findViewById(R.id.workerRole);
            workerName = itemView.findViewById(R.id.workerName);
        }
    }
}
