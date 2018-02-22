package mastersunny.unitedclub.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import mastersunny.unitedclub.Fragments.CategoryFragment;
import mastersunny.unitedclub.Fragments.FoodFragment;
import mastersunny.unitedclub.Model.CategoryDTO;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<CategoryDTO> DTOs;
    private ArrayList<String> titles;


    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
        DTOs = new ArrayList<>();
        titles = new ArrayList<>();
    }

    public void addItems(ArrayList<CategoryDTO> DTOs) {
        if (DTOs != null) {
            for (CategoryDTO dto : DTOs) {
                this.DTOs.add(dto);
                titles.add(dto.getCategoryName());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FoodFragment.newInstance(DTOs.get(position));
        } else {
            return CategoryFragment.newInstance(DTOs.get(position));
        }
    }

    @Override
    public int getCount() {
        return DTOs == null ? 0 : DTOs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
