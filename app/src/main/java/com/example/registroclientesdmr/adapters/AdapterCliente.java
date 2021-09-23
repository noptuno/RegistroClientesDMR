package com.example.registroclientesdmr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registroclientesdmr.R;
import com.example.registroclientesdmr.clase.Cliente;

import java.util.ArrayList;
import java.util.List;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.NoteViewHolder>{

    private List<Cliente> notes;

    private OnNoteSelectedListener onNoteSelectedListener;

    private Context contex;

    public AdapterCliente() {
        this.notes = new ArrayList<>();
    }

    public AdapterCliente(List<Cliente> notes) {
        this.notes = notes;
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View elementoTitular = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_cliente, parent, false);

        contex = elementoTitular.getContext();

        return new NoteViewHolder(elementoTitular);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder view, int pos) {
        view.bind(notes.get(pos));
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }


    public List<Cliente> getNotes() {
        return notes;
    }

    public void setNotes(List<Cliente> notes) {
        this.notes = notes;
    }

    public void setOnNoteSelectedListener(OnNoteSelectedListener onNoteSelectedListener) {
        this.onNoteSelectedListener = onNoteSelectedListener;
    }


    public interface OnNoteSelectedListener {
        void onClick(Cliente note);
    }



    public class NoteViewHolder extends RecyclerView.ViewHolder {


        private TextView nombre,empresa,vendedor;


        public NoteViewHolder(View item) {
            super(item);

            nombre = (TextView) item.findViewById(R.id.txt_nombre);
            empresa = (TextView) item.findViewById(R.id.txt_empresa);
            vendedor = (TextView) item.findViewById(R.id.txt_vendedor);


        }

        public void bind(final Cliente cliente) {

            nombre.setText(cliente.getNombre());
            empresa.setText(cliente.getEmpresa().toString());
            vendedor.setText(cliente.getEmail_usuario().toString());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteSelectedListener != null) {
                        onNoteSelectedListener.onClick(cliente);
                    }
                }
            });

        }


    }

}
