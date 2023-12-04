const contenedorTarjetas = document.getElementById("productos-container")
const unidadesElement = document.getElementById("unidades")
const precioElement = document.getElementById("precio")
const carritoVacioElement = document.getElementById("carrito-vacio");
const totalesElement = document.getElementById("totales");
const reiniciarCarritoElement = document.getElementById("reiniciar");
const realizarPedidoElement = document.getElementById("realizarPedido");


function crearTarjetasProductosInicio() {
    contenedorTarjetas.innerHTML = "";
    const productos = JSON.parse(localStorage.getItem("productos"));

    if (productos && productos.length > 0) {
        // Crear la tabla y la fila de encabezado
        const tabla = document.createElement("table");
        const encabezado = document.createElement("tr");
        encabezado.innerHTML = `
            <th>Imagen</th>
            <th>Descripción</th>
            <th>Precio U.</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        `;
        tabla.appendChild(encabezado);

        // Agregar filas de productos a la tabla
        productos.forEach(producto => {
            const filaProducto = document.createElement("tr");
            filaProducto.innerHTML = `
                <td><img class='imagenProducto' src=${producto.imagen}></td>
                <td class='nombreProducto'>${producto.nombre}</td>
                <td class='precioProducto'>${producto.precio}</td>
                <td class='cantidadProducto'>
                    <button class='btn btn-primary btn-sm restar'>-</button>
                    <span class='cantidad' >${producto.cantidad}</span>
                    <button class='btn btn-primary btn-sm sumar'>+</button>
                </td>
                <td class="total-producto">${producto.precio * producto.cantidad}</td>
            `;

            filaProducto.querySelector(".restar").addEventListener("click", (e) => {
                restarAlCarrito(producto);
                actualizarTotales();
                crearTarjetasProductosInicio();
            });

            filaProducto.querySelector(".sumar").addEventListener("click", (e) => {
                const cuentaElement = e.target.parentElement.querySelector(".cantidad");
                cuentaElement.innerText = agregarAlCarrito(producto);
                actualizarTotales();
                crearTarjetasProductosInicio();
            });

            tabla.appendChild(filaProducto);
        });

        // Agregar la tabla al contenedor
        contenedorTarjetas.appendChild(tabla);
    }
}


function agregarAlCarrito(producto){
    const memoria = JSON.parse(localStorage.getItem("productos"))
    let cuenta = 0;
    if(!memoria){
    const nuevoProducto = getNuevoProductoParaMemoria(producto);
    localStorage.setItem("productos", JSON.stringify([nuevoProducto]));
    cuenta = 1;
    }else{
        const indiceProducto = memoria.findIndex(prod => prod.nombre === producto.nombre )
        const nuevaMemoria = memoria;

        if(indiceProducto === -1 ){
            nuevaMemoria.push(getNuevoProductoParaMemoria(producto));
            cuenta = 1;
        }else{
            nuevaMemoria[indiceProducto].cantidad ++;
            cuenta = nuevaMemoria[indiceProducto].cantidad;
        }
        localStorage.setItem("productos", JSON.stringify(nuevaMemoria));
    }
    return cuenta;
}

function restarAlCarrito(producto){
    const memoria = JSON.parse(localStorage.getItem("productos"))
    const indiceProducto = memoria.findIndex(prod => prod.nombre === producto.nombre )


    if(memoria[indiceProducto].cantidad === 1){
        memoria.splice(indiceProducto,1)
    }else{
        memoria[indiceProducto].cantidad--;
    }
    localStorage.setItem("productos", JSON.stringify(memoria));
}

function actualizarTotales(){
    const productos = JSON.parse(localStorage.getItem("productos"));
    let unidades = 0;
    let precio = 0;
    if(productos && productos.length > 0){
    productos.forEach(producto => {
        unidades += producto.cantidad;
        precio += producto.precio * producto.cantidad;
    })
    }
    unidadesElement.innerText = unidades;
    precioElement.innerText = precio;
    revisarMensajeVacio()
}

function revisarMensajeVacio(){
    const productos = JSON.parse(localStorage.getItem("productos"));
    carritoVacioElement.classList.toggle("escondido", productos && productos.length > 0);
    totalesElement.classList.toggle("escondido", !(productos && productos.length>0));

}

reiniciarCarritoElement.addEventListener("click", reiniciarCarrito)
realizarPedidoElement.addEventListener("click", enviarDatosPedido)

function reiniciarCarrito(){
    localStorage.removeItem("productos");
    actualizarTotales();
    crearTarjetasProductosInicio();
}

function enviarDatosPedido(){
    var url = window.location.href;
    var urlObj = new URL(url);
    urlObj.pathname = "/carrito/enviarPedido/";
    var nuevaUrl = urlObj.href;
    const listaProductos = JSON.parse(localStorage.getItem("productos"))

    const requestOptions = {
        method : "POST",
        headers : {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(listaProductos),

    }

    let inputNombre = document.getElementById('nombre').value;
    let inputCorreo = document.getElementById('correoElectronico').value;

    if( inputNombre != '' && inputCorreo !== ''){

    let nombreSinEspacios = inputNombre.replace(/ /g, "-");

    //fetch para enviar datos al backend
    fetch(nuevaUrl + nombreSinEspacios + '/' + inputCorreo, requestOptions)
    .then(response => {
        if(response.status === 201){
            mostrarVentanaModal("Pedido enviado correctamente!")
        }else{
            throw new Error("Error de red: " + response)
        }

    })
    .catch(error => {
        console.log("Error: " + error)
    })
}else{
    alert("Los campos nombre y telefono deben estar completos!")
}

}

function mostrarVentanaModal(mensaje) {
    // Crea un elemento de modal usando jQuery y Bootstrap
    var modal = $('<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">' +
      '<div class="modal-dialog" role="document"> ' +
        '<div class="modal-content">' +
          '<div class="modal-header">' +
            '<h5 class="modal-title" id="exampleModalLabel">Mensaje</h5>' +
            '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
              '<span aria-hidden="true">&times;</span> ' +
            '</button>' +
          '</div>' +
          '<div class="modal-body">' + mensaje + '</div>' +
          '<div class="modal-footer">' +
            '<a type="button" class="btn btn-primary" href="/" data-dismiss="modal">Cerrar</a>' +
         ' </div>' +
        '</div>' +
      '</div>' +
    '/div>');

    // Agrega el modal al cuerpo del documento
    $('body').append(modal);

    // Muestra el modal
    $('#myModal').modal('show');

    // Limpia el modal cuando se cierra
    $('#myModal').on('hidden.bs.modal', function () {
      $(this).remove();
    });
  }


crearTarjetasProductosInicio()
actualizarTotales()
revisarMensajeVacio()





