document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const deleteModal = document.getElementById('deleteModal');
    const confirmInput = document.getElementById('confirmInput');
    const cancelBtn = document.getElementById('cancelBtn');
    const confirmBtn = document.getElementById('confirmBtn');
    const modalContent = document.querySelector('.modal-content');

    let personaToDelete = null;

    // Delegación de eventos para manejar botones dinámicos
    document.addEventListener('click', function(e) {
        // Manejar clic en botón eliminar
        if (e.target.classList.contains('delete-btn') || e.target.closest('.delete-btn')) {
            e.preventDefault();
            const deleteBtn = e.target.classList.contains('delete-btn') ?
                e.target : e.target.closest('.delete-btn');

            const personaItem = deleteBtn.closest('.persona-item');
            personaToDelete = {
                dni: deleteBtn.getAttribute('data-dni'),
                nombre: personaItem.querySelector('span:nth-of-type(1)').textContent,
                apellido: personaItem.querySelector('span:nth-of-type(2)').textContent,
                email: personaItem.querySelector('span:nth-of-type(4)').textContent
            };

            showModal();
        }
    });

    // Mostrar modal con animación
    function showModal() {
        if (!deleteModal) {
            console.error('Modal no encontrado');
            return;
        }

        // Activar modal y bloquear scroll
        deleteModal.classList.add('active');
        document.body.classList.add('modal-open');

        // Resetear y enfocar input
        confirmInput.value = '';
        confirmInput.focus();
    }

    // Ocultar modal con animación
    function hideModal() {
        if (!deleteModal) return;

        // Desactivar modal y restaurar scroll
        deleteModal.classList.remove('active');
        document.body.classList.remove('modal-open');

        personaToDelete = null;
    }

    // Configurar event listeners
    if (cancelBtn) {
        cancelBtn.addEventListener('click', hideModal);
    } else {
        console.error('Botón cancelar no encontrado');
    }

    if (confirmBtn) {
        confirmBtn.addEventListener('click', confirmDeletion);
    } else {
        console.error('Botón confirmar no encontrado');
    }

    // Función para confirmar eliminación
    function confirmDeletion() {
        if (!confirmInput) {
            console.error('Input de confirmación no encontrado');
            return;
        }

        if (confirmInput.value.trim() === 'Confirmar') {
            deletePersona();
        } else {
            alert('Por favor escriba exactamente "Confirmar" para proceder');
            confirmInput.focus();
        }
    }

    // Función para eliminar persona
    function deletePersona() {
        if (!personaToDelete) return;

        fetch(`/api/personas/${personaToDelete.dni}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                showSuccessMessage();
                setTimeout(() => {
                    location.reload();
                }, 1500); // Recargar después de 1.5 segundos
            })
            .catch(error => {
                console.error('Error:', error);
                alert(`Error al eliminar: ${error.message}`);
            })
            .finally(hideModal);
    }

    // Mostrar mensaje de éxito
    function showSuccessMessage() {
        alert(`Persona eliminada correctamente\nDNI: ${personaToDelete.dni}`);
    }

    // Cerrar modal al hacer clic fuera del contenido
    if (deleteModal && modalContent) {
        deleteModal.addEventListener('click', function(e) {
            if (e.target === deleteModal) {
                hideModal();
            }
        });
    }

    // Cerrar modal con la tecla Escape
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' && deleteModal && deleteModal.classList.contains('active')) {
            hideModal();
        }
    });

    // Prevenir el cierre accidental al hacer clic dentro del contenido del modal
    if (modalContent) {
        modalContent.addEventListener('click', function(e) {
            e.stopPropagation();
        });
    }
});