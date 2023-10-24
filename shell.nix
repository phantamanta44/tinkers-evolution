{ pkgs ? import <nixpkgs> {}
, unstable ? import <nixos-unstable> {} }:

let
  zulu17 = unstable.zulu17;
  zulu8 = pkgs.zulu8;

  gradlePropPrefix = "ORG_GRADLE_PROJECT_";

  runtimeDeps = with pkgs; [ libpulseaudio libGL glfw openal stdenv.cc.cc.lib udev ]
    ++ (with xorg; [ libX11 libXext libXcursor libXrandr libXxf86vm ]);
in

pkgs.mkShell {
  nativeBuildInputs = [ zulu17 ];
  buildInputs = [ zulu8 ];

  "${gradlePropPrefix}org.gradle.java.installations.auto-detect" = false;
  "${gradlePropPrefix}org.gradle.java.installations.auto-download" = false;
  "${gradlePropPrefix}org.gradle.java.installations.paths" = "${zulu17},${zulu8}";

  LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath runtimeDeps;
}
