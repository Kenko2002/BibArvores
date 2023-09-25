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
        pilhaNavegacao = new ArrayList<NoExemplo<T>>(); // Inicializa a pilha de navegação.

        while (true) {
            int comparacao = comparador.compare(novoValor, atual.getValor());
            pilhaNavegacao.add(atual); // Adiciona o nó atual à pilha de navegação.

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

        if (raiz == null) { // A árvore está vazia, não há nada.
            return null;
        }

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

        if (raiz == null) {
            return null;
        }

        NoExemplo<T> pai = null;
        atual = raiz;

        while (atual != null) {
            int comparacao = comparador.compare(valor, atual.getValor());

            /*
            * Seguinte, aqui é o mesmo esquema que pesquisar, a arvore vai começar a ser
            * varrida até encontrar o valor. Uma vez encontrado a var atual vai receber
            * o valor e a var pai vai referenciar o nó pai de atual. Então quando,
            * comparacao == 0 ele termina o loop de busca já que atual vai ser valor.
            */
            if (comparacao == 0) {
                break;
            } else if (comparacao < 0) {
                pai = atual;
                atual = atual.getFilhoEsquerda();
            } else {
                pai = atual;
                atual = atual.getFilhoDireita();
            }
        }

        if (atual == null) {
            return null;
        }

        //Fim da pesquisa. Agora temos que remover o nó, mas caso ele tenha filhos?
        // Caso 1: Se o nó atual for uma folha (não tem filhos), simplesmente remova-o.
        if (atual.getFilhoEsquerda() == null && atual.getFilhoDireita() == null) {
            if (pai == null) { //Se pai é nulo, então atual é raiz.
                raiz = null;
            } else if (atual == pai.getFilhoEsquerda()) { //Se não for, verifica se o nó atual é o filho esquerda ou direita do pai (talvez dê para melhorar já dizendo qual lado do pai é atual.)
                pai.setFilhoEsquerda(null);
            } else {
                pai.setFilhoDireita(null);
            }
            return atual.getValor();
        }

        // Caso 2: Se o nó atual tem um filho (esquerda ou direita).
        if (atual.getFilhoEsquerda() != null && atual.getFilhoDireita() == null) {
            if (pai == null) {
                raiz = atual.getFilhoEsquerda();
            } else if (atual == pai.getFilhoEsquerda()) {
                pai.setFilhoEsquerda(atual.getFilhoEsquerda());
            } else {
                pai.setFilhoDireita(atual.getFilhoEsquerda());
            }
            return atual.getValor();
        }

        if (atual.getFilhoDireita() != null && atual.getFilhoEsquerda() == null) {
            if (pai == null) {
                // O nó a ser removido é a raiz da árvore.
                raiz = atual.getFilhoDireita();
            } else if (atual == pai.getFilhoEsquerda()) {
                pai.setFilhoEsquerda(atual.getFilhoDireita());
            } else {
                pai.setFilhoDireita(atual.getFilhoDireita());
            }
            return atual.getValor();
        }
        return atual.getValor();
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