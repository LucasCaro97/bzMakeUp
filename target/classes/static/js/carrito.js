const botonesAgregarCarrito = document.querySelectorAll("#agregarCarrito");
botonesAgregarCarrito.forEach((boton) => {
        boton.addEventListener("click", function () {
        const imagen = this.closest(".card").querySelector("img");
        const src = imagen.src;
        console.log(src);

        const productoSeleccionado = {
                nombre: this.closest(".card").querySelector(".descripcion").textContent, // Captura el nombre
                precio: parseFloat(this.closest(".card").querySelector(".precio").textContent.split("Precio: ")[1].replace('|', '')), // Captura el precio
                cantidad : 1, // Agrega otros detalles del producto que necesites
                imagen : src
            };

        console.log("Producto Seleccionado: " + productoSeleccionado.nombre)
        agregarAlCarrito(productoSeleccionado);
})
})



function agregarAlCarrito(producto){
    const memoria = JSON.parse(localStorage.getItem("productos"))
    console.log(memoria)
    if(!memoria){
    const nuevoProducto = getNuevoProductoParaMemoria(producto);
    localStorage.setItem("productos", JSON.stringify([nuevoProducto]));
    }else{
        console.log(producto.nombre)
        const indiceProducto = memoria.findIndex(prod => prod.nombre === producto.nombre )
        console.log(indiceProducto)
        if(indiceProducto === -1 ){
            const nuevaMemoria = memoria;
            nuevaMemoria.push(getNuevoProductoParaMemoria(producto));
            localStorage.setItem("productos", JSON.stringify(nuevaMemoria));
        }else{
            const nuevaMemoria = memoria;
            nuevaMemoria[indiceProducto].cantidad ++;
            localStorage.setItem("productos", JSON.stringify(nuevaMemoria));
        }

    }
    actualizarNumeroCarrito();
}


function getNuevoProductoParaMemoria(producto){
    const nuevoProducto = producto;
    return nuevoProducto;
}

const cuentaCarritoElement = document.getElementById("cuentaCarrito")
function actualizarNumeroCarrito(){
    const memoria = JSON.parse(localStorage.getItem("productos"));
    const cuenta = memoria.reduce((acum, current) => acum+current.cantidad,0)
    cuentaCarritoElement.innerText = cuenta;

}

actualizarNumeroCarrito()