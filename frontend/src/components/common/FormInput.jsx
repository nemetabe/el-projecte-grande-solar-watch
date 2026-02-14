import React from "react";

const FormInput = React.forwardRef(function FormInput(
  {
    label,
    id,
    name,
    type = "text",
    value,
    onChange,
    placeholder,
    required = false,
    className = "",
    ...rest
  },
  ref
) {
  return (
    <div className="form-input">
      {label && (
        <label htmlFor={id}>
          {label}
        </label>
      )}

      <input
        ref={ref}
        id={id}
        name={name}
        type={type}
        value={value ?? ""}
        onChange={onChange}
        placeholder={placeholder}
        required={required}
        className={className}
        {...rest}
      />
    </div>
  );
});

export default FormInput;