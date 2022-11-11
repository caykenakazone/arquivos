package com.apmvc.apartamento.Controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apmvc.apartamento.Model.Propietario;

import org.springframework.ui.Model;

import java.util.List;

@Controller
public class HomePropietario {
    @Autowired
    JdbcTemplate db;

    @GetMapping("/cadastrarProp")
    public String exibeFormProp(Model model) {
        model.addAttribute("propietario", new Propietario());
        return "cadastrarProp";
    }


    @PostMapping("cadastrarProp")
    public String gravaDadosProp(Propietario proprietario) {
        db.update("insert into propietario (nome, telefone, idProp) values (?,?,?)",
                proprietario.getNome(), proprietario.getTelefone(), proprietario.getIdProp());
        return "redirect:/cadastrarProp";
    }

    @GetMapping("/listarProp")
    public String listarProp(Model model) {
        List<Propietario> listaDePropietarios = db.query(
                "select * from propietario",
                (res, rowNum) -> {
                    Propietario prop = new Propietario();
                            prop.setNome(res.getString("nome"));
                            prop.setTelefone(res.getString("telefone"));
                            prop.setIdProp(res.getInt("idProp"));
                            // res.getString("nome"),
                            // res.getString("telefone"),
                            // res.getString("idProp"));
                    return prop;

                });
        model.addAttribute("propietarios", listaDePropietarios);
        return "listarProp";
    }

    @GetMapping("excluirProp")
    public String apagarProprietario(@RequestParam(value = "idProp", required = true) int idProp) {
        db.update("delete from proprietario where idProp=?", idProp);
        return "redirect:/listarProp";
    }

    @GetMapping("editarProp")
    public String exibeFormAlteracaoProprietario(@RequestParam(value = "idProp", required = true) int idProp,
            Model model) {
        Propietario proprietario = db.queryForObject(
                "update * from proprietario where idProp = ?",
                (rs, rowNum) -> {
                    Propietario edited = new Propietario();
                    edited.setIdProp(rs.getInt("idProp"));
                    edited.setNome(rs.getString("nome"));
                    edited.setTelefone(rs.getString("telefone"));
                    return edited;
                }, idProp);
        model.addAttribute("proprietarioEditado", proprietario);
        return "editarProp";
    }

    @PostMapping("gravaproprietarioeditado")
    public String gravaProprietarioEditado(Propietario proprietario) {
        db.update(
                "update proprietario set nome=?, telefone=? where idProp = ?",
                proprietario.getNome(),
                proprietario.getTelefone(),
                proprietario.getIdProp());
        return "redirect:/listarProp";
    }

}
