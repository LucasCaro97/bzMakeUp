  function darMargenAlCollapse(){
            const collapseButton = document.getElementById('collapseMenu');
            const heroElement = document.querySelector('.hero');

            // Agrega un evento de clic al botón
            collapseButton.addEventListener('click', function() {
                // Verifica el valor de aria-expanded
                const isExpanded = collapseButton.getAttribute('aria-expanded') === 'true';

                // Ajusta la altura según el valor de aria-expanded
                if (isExpanded) {
                    heroElement.style.height = '175px';
                } else {
                    // Puedes establecer la altura a su valor original o dejarla vacía para usar el estilo predeterminado
                    heroElement.style.height = '';
                }
            });
  }

darMargenAlCollapse();