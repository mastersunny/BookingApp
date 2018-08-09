package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.listeners.ExamSelectionListener;
import mastersunny.unitedclub.models.ExamDTO;
import mastersunny.unitedclub.utils.Constants;

public class ExamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ExamDTO> examDTOList;
    private Activity mActivity;
    private int selectedIndex = -1;
    private ExamSelectionListener examSelectionListener;
    private String TAG = "ExamAdapter";

    public ExamAdapter(Activity mActivity, List<ExamDTO> examDTOList) {
        this.mActivity = mActivity;
        this.examDTOList = examDTOList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ExamDTO examDTO = examDTOList.get(position);
        MainHolder mainHolder = (MainHolder) holder;
        if (selectedIndex == position) {
            holder.itemView.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.itemView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
        }
        mainHolder.exam_name.setText(examDTO.getName());
        mainHolder.exam_date.setText(examDTO.getExamDate());
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndex == position) {
                    selectedIndex = -1;
                } else {
                    if (examSelectionListener != null) {
                        examSelectionListener.selectedExam(examDTO);
                    }
                    selectedIndex = position;
                }
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return examDTOList == null ? 0 : examDTOList.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.exam_name)
        TextView exam_name;

        @BindView(R.id.exam_date)
        TextView exam_date;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(ExamSelectionListener examSelectionListener) {
        this.examSelectionListener = examSelectionListener;
    }
}
