import React from "react";
import NavBar from "../components/navBar";
import Rotas from "./rotas";
import 'toastr/build/toastr.min.js';
import 'bootswatch/dist/flatly/bootstrap.css';
import '../custom.css';
import 'toastr/build/toastr.css';
import 'primereact/styleclass';
import "../button-demo.css";
import "primereact/resources/primereact.min.css";                  //core css
import "primeicons/primeicons.css"; 
import AuthProvider from "./ProvedorAutenticacao";
class App extends React.Component {

  render() {
    return (
      <AuthProvider>
        <NavBar />
        <div className="container">
          <Rotas />
        </div>
      </AuthProvider>
    )
  }
}

export default App;
