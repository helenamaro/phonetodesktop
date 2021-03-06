package net.xisberto.phonetodesktop;

import java.util.Vector;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AccountListFragment extends DialogFragment {
	private Account[] accounts;
	private AccountSelector selector;

	public static AccountListFragment newInstance(Account[] acc) {
		AccountListFragment dialog = new AccountListFragment();

		Bundle args = new Bundle();
		args.putParcelableArray("accounts", acc);
		dialog.setArguments(args);

		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NORMAL, R.style.Theme_Sherlock_Light_Dialog_Titlebar);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		accounts = (Account[]) getArguments().getParcelableArray("accounts");
		
		Vector<String> names = new Vector<String>();
		for (int i = 0; i < accounts.length; i++) {
			names.add(accounts[i].name);
		}
		
		View v = inflater.inflate(R.layout.layout_list, container, false);
		ListView list = (ListView) v.findViewById(R.id.list_accounts);
		list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names));
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selector.selectAccount(accounts[position]);
			}
		});

		if (getShowsDialog()) {
			getDialog().setTitle(R.string.title_select_account);
		}
		
		return v;
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			selector = (AccountSelector) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "must implment AccountSelector");
		}
	}

	public interface AccountSelector {
		public void selectAccount(Account acc);
	}

}