import React from "react";
import {ReactComponent as MainImage} from '../../assets/images/main-image.svg';
import ButtonIcon from "../../components/ButtonIcon";
import { Link } from 'react-router-dom'
import './styles.scss';

const Home = () => (
    <div className = "home-container">
        <div className="row home-content card-base border-radius-20">
            <div className="col-6">
                <h1 className= "text-title">
                     catálogo de produtos</h1>
                <p className="text-subtitle">
                 Desafio Lampp It - Produtos ofertados.
             </p>
             <Link to="/products" className="button-link-home">
             <ButtonIcon  text="Ver produtos ofertados" />
             </Link>
             <br />
             <Link to="/company" className="button-link-home">
             <ButtonIcon text="Ver empresas cadastradas"/>
             </Link> 
            </div>
           <div className="col-6">
               <MainImage className="main-image" />

           </div>
        </div>
    </div>

);

export default Home;