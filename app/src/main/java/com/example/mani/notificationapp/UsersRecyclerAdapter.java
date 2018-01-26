package com.example.mani.notificationapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mani on 1/26/18.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {

    private List<Users> usersList;

    public UsersRecyclerAdapter(List<Users> usersList) {
        this.usersList = usersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mUsersTextview.setText(usersList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView mUsersTextview;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mUsersTextview = (TextView) mView.findViewById(R.id.tv_users);
        }
    }
}
