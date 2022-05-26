import React from "react";
import currencyFromater from 'currency-formatter'
const LancamentosTable = (props) => {


    const rows = props.lancamentos.map(lancamento => {
        return (
            <tr key={lancamento.id}>
                <td>{lancamento.descricao}</td>
                <td>{currencyFromater.format(lancamento.valor, { locale: 'pt-BR' })}</td>
                <td>{lancamento.tipo}</td>
                <td>{lancamento.mes}</td>
                <td>{lancamento.statusLancamento}</td>
                <td>
                    <button onClick={e => props.alterarStatus(lancamento, 'EFETIVADO')} type="button" className="btn btn-success" disabled={lancamento.statusLancamento !== 'PENDENTE'}>Efetivado</button>
                    <button onClick={e => props.alterarStatus(lancamento, 'CANCELADO')} type="button" className="btn btn-warning" disabled={lancamento.statusLancamento !== 'PENDENTE'}>Cancelar</button>
                    <button type="button" className="btn btn-primary" onClick={e => props.editAction(lancamento.id)}>Editar</button>
                    <button type="button" className="btn btn-danger" onClick={e => props.deletAction(lancamento)}>Deletar</button>
                </td>
            </tr>
        )
    })



    return (
        <table className="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Descrição</th>
                    <th scope="col">Valor</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Mês</th>
                    <th scope="col">Situação</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                {rows}
            </tbody>
        </table>
    )
}

export default LancamentosTable