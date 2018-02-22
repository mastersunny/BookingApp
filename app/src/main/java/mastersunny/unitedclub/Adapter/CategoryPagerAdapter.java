package mastersunny.unitedclub.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import mastersunny.unitedclub.Fragments.CategoryFragment;
import mastersunny.unitedclub.Model.CategoryDTO;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<CategoryDTO> dtos;


    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
        dtos = new ArrayList<>();
    }

    public void addItems(ArrayList<CategoryDTO> dtos) {
        this.dtos.addAll(dtos);
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(dtos.get(position));
    }

    @Override
    public int getCount() {
        return dtos == null ? 0 : dtos.size();
    }
}
