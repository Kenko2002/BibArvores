/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author victoriocarvalho
 */
public class ArvoreBinariaExemplo<T> implements IArvoreBinaria<T> {
    
    protected NoExemplo<T> raiz = null;
    protected Comparator<T> comparador; 
    
    protected NoExemplo<T> atual = null;
    private ArrayList<NoExemplo<T>> pilhaNavegacao = null;

    public ArvoreBinariaExemplo(Comparator<T> comp) {
        comparador = comp;
    }


    @Override
    public void adicionar(T novoValor) {
        NoExemplo<T> novoNo = new NoExemplo<T>(novoValor);

        if (raiz == null) {
            raiz = novoNo;
            return; // Encerra o método adicionar quando a árvore está vazia.
        }

        atual = raiz;
        while (true) {
            int comparacao = comparador.compare(novoValor, atual.getValor());
            if (comparacao < 0) {
                if (atual.getFilhoEsquerda() == null) {
                    atual.setFilhoEsquerda(novoNo);
                    return; // Encerra o método quando o novo valor foi adicionado.
                }
                atual = atual.getFilhoEsquerda();
            } else if (comparacao > 0) {
                if (atual.getFilhoDireita() == null) {
                    atual.setFilhoDireita(novoNo);
                    return; // Encerra o método quando o novo valor foi adicionado.
                }
                atual = atual.getFilhoDireita();
            } else {
                // Se a diferença do comparador for 0, então já existe, se valor já existe na árvore, não faz nada. Ou podemos fazer com que todos nós duplicados sejam adicionado à direita. *Cada elemento possui um ramo esquerda (menores) e um ramo direito (maiores ou iguais)*
                return;
            }
        }
    }

    @Override
    public T pesquisar(T valor) {
        atual = raiz;

        while (atual != null) {
            int comparacao = comparador.compare(valor, atual.getValor());

            if (comparacao == 0) {
                return atual.getValor(); // Valor encontrado.
            } else if (comparacao < 0) {
                // O valor pode estar na subárvore esquerda.
                atual = atual.getFilhoEsquerda();
            } else {
                // O valor pode estar na subárvore direita.
                atual = atual.getFilhoDireita();
            }
        }

        return null; // Valor não encontrado na árvore.
    }

    @Override
    public T remover(T valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int altura() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    
    @Override
    public int quantidadeNos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String caminharEmNivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }
    
    @Override
    public String caminharEmOrdem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }
    
    
    @Override
    public T obterProximo(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void reiniciarNavegacao(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
