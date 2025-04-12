document.addEventListener('DOMContentLoaded', function(e) {
    // Elementos del DOM
    const deleteModal = document.getElementById('deleteModal');
    const feedbackModal = document.getElementById('feedbackModal');
    const confirmInput = document.getElementById('confirmInput');
    const cancelBtn = document.getElementById('cancelBtn');
    const confirmBtn = document.getElementById('confirmBtn');
    const feedbackOkBtn = document.getElementById('feedbackOkBtn');
    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    const sortSelect = document.getElementById('sortSelect');

    let personaToDelete = null;

    // Delegación de eventos para botones de eliminar
    document.addEventListener('click', function(e) {
        const deleteBtn = e.target.closest('.delete-btn');

        if (deleteBtn) {
            e.preventDefault();
            const personaItem = deleteBtn.closest('.persona-item');

            if (!personaItem) {
                console.error('No se encontró el contenedor de persona');
                return;
            }

            personaToDelete = {
                dni: deleteBtn.getAttribute('data-dni'),
                nombre: personaItem.querySelector('[data-field="nombre"]').textContent,
                apellido: personaItem.querySelector('[data-field="apellido"]').textContent
            };

            showModal(deleteModal);
        }
    });

    // Eventos del modal de confirmación
    cancelBtn.addEventListener('click', function(e) {
        e.preventDefault();
        hideModal(deleteModal);
        showFeedback('Operación cancelada', 'La eliminación fue cancelada.');
    });

    confirmBtn.addEventListener('click', function(e) {
        e.preventDefault();

        if (confirmInput.value.trim().toUpperCase() === 'CONFIRMAR') {
            // Enviar el DNI al backend para eliminar con verificación de seguridad
            fetch(`/eliminar-persona/${personaToDelete.dni}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest' // Header de seguridad
                },
                credentials: 'include' // Para manejo de sesiones/cookies
            })
                .then(response => {
                    if (response.status === 403) {
                        throw new Error('No tienes permiso para realizar esta acción');
                    }
                    if (!response.ok) {
                        throw new Error('Error al eliminar la persona');
                    }
                    return response.json();
                })
                .then(data => {
                    showFeedback('Éxito', data.message || 'Persona eliminada correctamente');
                })
                .catch(error => {
                    console.error('Error:', error);
                    showFeedback('Error', error.message || 'Ocurrió un error al eliminar la persona');
                });

            hideModal(deleteModal);
        } else {
            showFeedback('Error', 'Debe escribir exactamente "CONFIRMAR" para proceder.');
        }
    });

    // Evento para el botón OK del feedback
    feedbackOkBtn.addEventListener('click', function(e) {
        e.preventDefault();
        hideModal(feedbackModal);
        window.location.href = '/menu';
    });

    // Filtrado y ordenación
    searchBtn.addEventListener('click', applyFilters);
    sortSelect.addEventListener('change', applyFilters);
    searchInput.addEventListener('keyup', function(e) {
        if (e.key === 'Enter') {
            applyFilters();
        }
    });

    function applyFilters() {
        const searchTerm = searchInput.value.trim().toLowerCase();
        const sortDirection = sortSelect.value;
        const personas = document.querySelectorAll('.persona-item');

        personas.forEach(persona => {
            const nombre = persona.querySelector('[data-field="nombre"]').textContent.toLowerCase();
            const apellido = persona.querySelector('[data-field="apellido"]').textContent.toLowerCase();
            const fullName = `${nombre} ${apellido}`;

            // Filtrado por búsqueda
            const matchesSearch = searchTerm === '' ||
                nombre.includes(searchTerm) ||
                apellido.includes(searchTerm) ||
                fullName.includes(searchTerm);

            persona.style.display = matchesSearch ? 'flex' : 'none';
        });

        // Ordenación (simulada, en realidad debería recargar desde el servidor)
        if (sortDirection === 'asc' || sortDirection === 'desc') {
            const personasContainer = document.querySelector('.personas-list');
            const personasArray = Array.from(personas).filter(p => p.style.display !== 'none');

            personasArray.sort((a, b) => {
                const nameA = a.querySelector('[data-field="nombre"]').textContent.toLowerCase();
                const nameB = b.querySelector('[data-field="nombre"]').textContent.toLowerCase();
                return sortDirection === 'asc' ?
                    nameA.localeCompare(nameB) :
                    nameB.localeCompare(nameA);
            });

            personasArray.forEach(persona => personasContainer.appendChild(persona));
        }
    }

    // Funciones auxiliares
    function showModal(modal) {
        if (!modal) return;
        modal.classList.add('active');
        document.body.classList.add('modal-open');

        if (modal === deleteModal) {
            confirmInput.value = '';
            confirmInput.focus();
        }
    }

    function hideModal(modal) {
        if (!modal) return;
        modal.classList.remove('active');
        document.body.classList.remove('modal-open');

        if (modal === deleteModal) {
            personaToDelete = null;
        }
    }

    function showFeedback(title, message) {
        const feedbackTitle = document.getElementById('feedbackTitle');
        const feedbackMessage = document.getElementById('feedbackMessage');

        feedbackTitle.textContent = title;
        feedbackMessage.textContent = message;

        showModal(feedbackModal);
    }

    // Cerrar modales haciendo clic fuera del contenido
    window.addEventListener('click', function(e) {
        if (e.target.classList.contains('modal')) {
            const activeModal = document.querySelector('.modal.active');
            if (activeModal) {
                hideModal(activeModal);
            }
        }
    });
});