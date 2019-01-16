package android.wakecap.co.wakecap.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mFragmentList:ArrayList<Fragment> = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList.size

    }

    fun addFragment(fragment : Fragment , title : String) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

//    override  fun  getPageTitle(position :Int) {
//        return mFragmentTitleList.get(position);
//    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return super.getPageTitle(position)
        return mFragmentTitleList[position]
    }
}